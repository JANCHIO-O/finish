package com.example.library.system;

import com.example.library.common.entity.UserAccount;
import com.example.library.common.repository.UserAccountRepository;
import com.example.library.system.entity.SystemInfo;
import com.example.library.system.entity.SystemPolicy;
import com.example.library.system.repository.SystemInfoRepository;
import com.example.library.system.repository.SystemPolicyRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SystemDataInitializer implements CommandLineRunner {

    private final UserAccountRepository userAccountRepository;
    private final SystemPolicyRepository systemPolicyRepository;
    private final SystemInfoRepository systemInfoRepository;

    public SystemDataInitializer(UserAccountRepository userAccountRepository,
                                 SystemPolicyRepository systemPolicyRepository,
                                 SystemInfoRepository systemInfoRepository) {
        this.userAccountRepository = userAccountRepository;
        this.systemPolicyRepository = systemPolicyRepository;
        this.systemInfoRepository = systemInfoRepository;
    }

    @Override
    public void run(String... args) {
        if (userAccountRepository.findByAccountIdAndRole("admin", "ADMIN").isEmpty()) {
            userAccountRepository.save(new UserAccount("admin", "ADMIN", "123456"));
        }
        systemPolicyRepository.findByRole("TEACHER")
                .orElseGet(() -> systemPolicyRepository.save(new SystemPolicy(
                        "TEACHER",
                        60,
                        20,
                        "超期按每天每册1元收取滞纳金，累计超过30天需补办借阅手续。"
                )));
        systemPolicyRepository.findByRole("STUDENT")
                .orElseGet(() -> systemPolicyRepository.save(new SystemPolicy(
                        "STUDENT",
                        30,
                        10,
                        "超期按每天每册2元收取滞纳金，累计超过15天将暂停借阅权限。"
                )));
        if (systemInfoRepository.findFirstByOrderByIdAsc().isEmpty()) {
            systemInfoRepository.save(new SystemInfo(
                    "智慧图书馆管理系统",
                    "面向校园读者与管理员的一体化管理平台，覆盖借阅、检索、账户、公告与统计等核心业务，确保服务连续稳定。",
                    "图书编目与检索、借还与续借、读者账户与权限、公告与消息推送、运营数据统计与报表。",
                    "支持教师与学生双角色服务，提供多终端访问体验，并预留与校园卡、门禁或财务系统的接口。",
                    "权限分级控制、操作日志追踪、异常提醒与数据定期备份，保障数据安全与审计合规。",
                    "系统配置可快速调整，支持规则更新、性能监控与故障定位，为后续扩展提供维护支持。"
            ));
        }
    }
}
