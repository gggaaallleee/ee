package wj.service;

import com.gm.wj.service.BookService;
import com.gm.wj.service.DashboardService;
import com.gm.wj.service.JotterArticleService;
import com.gm.wj.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DashboardServiceTest {

    @Autowired
    private DashboardService dashboardService;

    @MockBean
    private UserService userService;

    @MockBean
    private JotterArticleService jotterArticleService;

    @MockBean
    private BookService bookService;
    @Test
    public void testGetDashboardData() {
        when(userService.countUser()).thenReturn(10L);
        when(jotterArticleService.countArticle()).thenReturn(20L);
        when(bookService.countBook()).thenReturn(30L);
        when(jotterArticleService.countViews()).thenReturn(40L);

        Map<String, Integer> result = dashboardService.getDashboardData();

        assertEquals(10, result.get("userCount").intValue());
        assertEquals(20, result.get("articleCount").intValue());
        assertEquals(30, result.get("bookCount").intValue());
        assertEquals(40, result.get("viewsCount").intValue());
    }
}