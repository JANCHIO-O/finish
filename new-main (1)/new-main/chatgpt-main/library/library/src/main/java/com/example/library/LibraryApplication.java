package com.example.library;

import com.example.library.common.entity.AcceptanceRecord;
import com.example.library.common.entity.AcquisitionRecord;
import com.example.library.common.entity.CirculationBook;
import com.example.library.common.entity.ReaderInfo;
import com.example.library.common.repository.AcceptanceRecordRepository;
import com.example.library.common.repository.AcquisitionRecordRepository;
import com.example.library.common.repository.CirculationBookRepository;
import com.example.library.common.repository.ReaderInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Date;

@SpringBootApplication
public class LibraryApplication implements CommandLineRunner {

    @Autowired
    private AcceptanceRecordRepository acceptanceRecordRepository;

    @Autowired
    private AcquisitionRecordRepository acquisitionRecordRepository;

    @Autowired
    private CirculationBookRepository circulationBookRepository;

    @Autowired
    private ReaderInfoRepository readerInfoRepository;

    public static void main(String[] args) {
        SpringApplication.run(LibraryApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // 插入一些采访问卷数据
        if (!acquisitionRecordRepository.existsById("001")) {
            acquisitionRecordRepository.save(new AcquisitionRecord("001", "Java编程", "作者1", "1234567890123", "出版社1", "图书"));
        }
        if (!acquisitionRecordRepository.existsById("002")) {
            acquisitionRecordRepository.save(new AcquisitionRecord("002", "Spring入门", "作者2", "1234567890124", "出版社2", "教程"));
        }

        // 插入一些验收清单数据（包含与流通库重合的 ISBN）
        if (!acceptanceRecordRepository.existsById("A001")) {
            acceptanceRecordRepository.save(new AcceptanceRecord("A001", "Java编程", "1234567890123", "出版社1", "图书", "验收员甲", Date.valueOf("2025-03-01")));
        }
        if (!acceptanceRecordRepository.existsById("A002")) {
            acceptanceRecordRepository.save(new AcceptanceRecord("A002", "Spring入门", "1234567890124", "出版社2", "教程", "验收员乙", Date.valueOf("2025-03-02")));
        }
        if (!acceptanceRecordRepository.existsById("A003")) {
            acceptanceRecordRepository.save(new AcceptanceRecord("A003", "算法导论", "1234567890125", "出版社3", "教材", "验收员丙", Date.valueOf("2025-03-03")));
        }

        // 插入一些图书流通库数据
        if (!circulationBookRepository.existsById("1234567890123")) {
            circulationBookRepository.save(new CirculationBook("1234567890123", "B001", "Java编程", "2026-01-12"));
        }
        if (!circulationBookRepository.existsById("1234567890124")) {
            circulationBookRepository.save(new CirculationBook("1234567890124", "B002", "Spring入门", "2026-01-12"));
        }

        // 插入一些读者信息初始数据
        if (!readerInfoRepository.existsById("R1001")) {
            readerInfoRepository.save(new ReaderInfo("R1001", "张晨", "男", "13800000001", "身份证", "110101199001010011"));
        }
        if (!readerInfoRepository.existsById("R1002")) {
            readerInfoRepository.save(new ReaderInfo("R1002", "李静", "女", "13800000002", "身份证", "110101199202020022"));
        }
        if (!readerInfoRepository.existsById("R1003")) {
            readerInfoRepository.save(new ReaderInfo("R1003", "王磊", "男", "13800000003", "护照", "E12345678"));
        }
    }
}