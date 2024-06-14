package com.dvb.forum.scheduler;

import com.dvb.forum.security.TokenBlacklistService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TokenBlacklistCleanupJob implements Job {

    @Autowired
    private TokenBlacklistService tokenBlacklistService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        log.info("TokenBlacklistCleanupJob - execute - jobExecutionContext.getJobDetail().getKey().getName(): {}, jobExecutionContext.getFireTime(): {}",
                jobExecutionContext.getJobDetail().getKey().getName(), jobExecutionContext.getFireTime());
        tokenBlacklistService.removeExpiredTokensFromBlacklist();
        log.info("TokenBlacklistCleanupJob - execute - jobExecutionContext.getNextFireTime(): {}", jobExecutionContext.getNextFireTime());
    }

}
