package io.pvincent.pactworkshop.score;

import org.junit.Assert;
import org.junit.Test;

public class ScoresStorageServiceTest {

    @Test
    public void testThatHighestWinnerCanBeRetrieved() {
        ScoresStorageService service = new ScoresStorageService();

        service.recordScore("john", "tic-tac-toe", true);
        service.recordScore("john", "tic-tac-toe", true);
        service.recordScore("abby", "tic-tac-toe", true);
        service.recordScore("abby", "chess", true);
        service.recordScore("abby", "chess", true);
        service.recordScore("abby", "chess", false);
        service.recordScore("abby", "chess", true);
        service.recordScore("ash", "tic-tac-toe", true);
        service.recordScore("ash", "tic-tac-toe", false);
        service.recordScore("ash", "tic-tac-toe", false);
        service.recordScore("ash", "tic-tac-toe", false);
        service.recordScore("ash", "tic-tac-toe", false);
        service.recordScore("ash", "chess", true);
        service.recordScore("ash", "chess", false);

        Assert.assertEquals("john", service.getLeader("tic-tac-toe"));
        Assert.assertEquals("abby", service.getLeader("chess"));
        Assert.assertEquals("", service.getLeader("checkers"));
    }

}
