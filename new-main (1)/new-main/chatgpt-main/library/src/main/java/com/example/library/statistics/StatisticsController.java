package com.example.library.statistics;

import com.example.library.statistics.entity.StatisticsRecord;
import com.example.library.statistics.service.StatisticsService;
import com.example.library.statistics.view.ReaderBorrowSummary;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping
    public String index() {
        return "redirect:/statistics/stat";
    }

    @GetMapping("/stat")
    public String stat(Model model) {
        model.addAttribute("statPeriods", new String[]{"日", "周", "月", "年"});
        return "statistics/stat";
    }

    @PostMapping("/stat")
    public String submitStat(@RequestParam(value = "statPeriod", required = false) String statPeriod,
                             Model model) {
        if (statPeriod == null || statPeriod.isBlank()) {
            model.addAttribute("statPeriods", new String[]{"日", "周", "月", "年"});
            model.addAttribute("errorMessage", "请填写必填项");
            return "statistics/stat";
        }
        StatisticsRecord record = statisticsService.runStatistics(statPeriod);
        return "redirect:/statistics/report?statId=" + record.getStatId();
    }

    @GetMapping("/report")
    public String report(@RequestParam(value = "statId", required = false) String statId, Model model) {
        if (statId == null || statId.isBlank()) {
            StatisticsRecord latest = statisticsService.findLatest();
            if (latest == null) {
                model.addAttribute("errorMessage", "暂无统计报表记录，请先提交统计。");
                return "statistics/report";
            }
            statId = latest.getStatId();
        }
        StatisticsRecord record = statisticsService.findById(statId);
        if (record == null) {
            model.addAttribute("errorMessage", "未找到统计结果");
            return "statistics/report";
        }
        model.addAttribute("record", record);
        model.addAttribute("statDate", statisticsService.formatDate(record.getStatDate()));
        model.addAttribute("statTypeLabel", statisticsService.getStatTypeLabel());
        List<ReaderBorrowSummary> summaries = statisticsService.buildReaderSummaries(record);
        long activeReaderCount = summaries.stream()
                .filter(summary -> summary.getBorrowCount() > 0)
                .count();
        model.addAttribute("readerSummaries", summaries);
        model.addAttribute("activeReaderCount", activeReaderCount);
        return "statistics/report";
    }
}
