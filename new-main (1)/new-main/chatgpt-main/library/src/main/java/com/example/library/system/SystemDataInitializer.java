package com.example.library.system;

import com.example.library.common.entity.UserAccount;
import com.example.library.common.repository.UserAccountRepository;
import com.example.library.system.entity.SystemPolicy;
import com.example.library.system.repository.SystemPolicyRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SystemDataInitializer implements CommandLineRunner {

    private final UserAccountRepository userAccountRepository;
    private final SystemPolicyRepository systemPolicyRepository;

    public SystemDataInitializer(UserAccountRepository userAccountRepository,
                                 SystemPolicyRepository systemPolicyRepository) {
        this.userAccountRepository = userAccountRepository;
        this.systemPolicyRepository = systemPolicyRepository;
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
    }
}
