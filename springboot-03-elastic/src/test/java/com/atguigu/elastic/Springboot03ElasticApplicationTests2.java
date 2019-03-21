package com.atguigu.elastic;

import com.atguigu.elastic.bean.Book;
import com.atguigu.elastic.repository.BookRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * create by liuliang on 2019/2/19.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class Springboot03ElasticApplicationTests2 {

    @Autowired
    BookRepository bookRepository;

    /**
     * 添加
     */
    @Test
    public void add() {
        Book book = new Book();
        book.setId(1);
        book.setBookName("西游记");
        book.setAuthor("吴承恩");
        Book book2 = new Book(2, "戏子游记", "song");
        Book book3 = new Book(3, "世末歌者", "双笙");
        bookRepository.index(book);
        bookRepository.save(book2);  //和上面差不多
        bookRepository.save(book3);
    }

    /**
     * 删除
     */
    @Test
    public void delete() {
        bookRepository.deleteAll();
    }

    /**
     * 查询
     */
    @Test
    public void nameSelect() {
        for (Book book : bookRepository.findByBookNameLike("游")) {
            System.out.println(book);
        }
    }


}
