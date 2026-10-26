package com.ms.credit.service;

// Import necessary Spring framework and Java utility classes.
import org.springframework.scheduling.annotation.Scheduled;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ms.credit.dto.CreditScoreDTO;

/**
 * CreditScoreCronJobService is a service class that schedules and executes periodic tasks
 * related to credit scores using Spring's scheduling capabilities.
 */
@Service  // Marks this class as a Spring-managed service.
public class CreditScoreCronJobService {

    // Automatically injects CreditScoreService to access its methods.
    @Autowired
    private CreditScoreService creditScoreService; 

    /**
     * Scheduled method to process pending credit scores on an hourly basis.
     * It fetches credit scores that have been updated more than 24 hours ago and processes them.
     */
    @Scheduled(cron = "0 0 * * * *") // Cron expression for hourly execution: runs at the top of every hour.
    public void processPendingClaims() {
        // Fetch a list of credit scores that need to be updated or processed.
        List<CreditScoreDTO> creditScores = creditScoreService.fetchScoresUpdatedMoreThan24HoursAgo(); 
        for (CreditScoreDTO creditScore : creditScores) {
            try {
                // Attempt to process each credit score, here simplified to retrieving a score by user ID.
                creditScoreService.getCreditScoreByEmailId(creditScore.getUserId());
            } catch (Exception e) {
                // Log or handle the exception if processing fails.
                System.out.println("Error processing Credit ID: " + creditScore.getUserId() + " with error: " + e.getMessage());
            }
        }
    }
}



//ai explaination

//1. “Service class”
//@Service
//public class CreditScoreCronJobService
//@Service → tells Spring:
//        👉 “This class contains business logic”
//        Spring automatically creates and manages this class (bean)
//
//        💡 So this is not a controller (no API), it runs logic internally.
//
//        ⏰ 2. “Schedules tasks”
//@Scheduled(cron = "0 0 * * * *")
//
//This means:
//
//        👉 Spring will automatically run this method at fixed intervals
//
//        Your cron expression:
//        0 0 * * * *
//
//        ✔ Runs every hour at minute 0
//
//        1:00
//        2:00
//        3:00
//        ...
//
//        💡 No user/API call needed → it runs in background
//
//        🔁 3. “Executes periodic tasks”
//
//        This part refers to what your method does:
//
//public void processPendingClaims()
//
//        Every hour, it:
//
//        Step-by-step:
//        Fetch old records:
//        creditScoreService.fetchScoresUpdatedMoreThan24HoursAgo();
//
//        👉 Gets users whose credit score is outdated (older than 24 hrs)
//
//        Loop through them:
//        for (CreditScoreDTO creditScore : creditScores)
//        Recalculate/update score:
//        creditScoreService.getCreditScoreByEmailId(...)
//        Handle failures:
//        catch (Exception e)
//
//        👉 Prevents entire job from crashing
//
//        🔥 Simple Real-World Explanation
//
//        Think of this like:
//
//        🕒 A scheduled worker (cron job) that runs every hour and says:
//
//        “Let me check all users whose credit score is outdated and refresh them.”
//
//        🎯 Interview-Friendly Answer
//
//        You can say this:
//
//        “This is a Spring service class that
//        runs a scheduled background job using the @Scheduled annotation.
//        It executes every hour and processes credit scores that haven’t been updated
//        in the last 24 hours. It fetches those records, recalculates their scores,
//        and handles failures gracefully so that one failure doesn’t stop the entire batch.”