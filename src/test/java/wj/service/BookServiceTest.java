package wj.service;

import com.gm.wj.dao.BookDAO;
import com.gm.wj.entity.Book;
import com.gm.wj.redis.RedisService;
import com.gm.wj.service.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookServiceTest {
    @Autowired
    private BookService bookService;
    @MockBean
    private BookDAO bookDAO;

    @MockBean
    private RedisService redisService;
    @Test
    public void testList() {
        Book book = new Book();
        book.setId(1);
        List<Book> books = Arrays.asList(book);

        when(redisService.get("booklist")).thenReturn(null);
        when(bookDAO.findAll(Mockito.any(Sort.class))).thenReturn(books);

        List<Book> result = bookService.list();

        assertEquals(1, result.size());
        assertEquals(book, result.get(0));
    }
}