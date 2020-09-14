package com.demo.test;

import com.demo.dao.IUserDao;
import com.demo.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.ResourceBundle;

/*mybatis入门案例*/
public class MybatisTest {
    public static void main(String[] args) throws IOException {

        //1.读取配置文件
        InputStream in = Resources.getResourceAsStream("SqlMapConfig.xml");
          //配置文件的路径：1.使用类加载器，只能读取类路径的配置文件 2.使用ServletContext对象的getRealPath()

        //2.创建SqlSessionFactory工厂
//        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
//        SqlSessionFactory factory = builder.build(in);
        //两句相当于下面一句
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
          //创建工厂mybatis使用构建者模式 ：builder是构建者（包工队） in是用户给的钱 包工队给钱办事（怎么办事被封装了故省略） 用户收到factory就是拿到的结果

        //3.使用工厂生产SqlSession对象
        SqlSession session = factory.openSession();
          //生产SqlSession使用工厂模式：解耦（降低类之间的依赖关系）

        //4.使用SqlSession创建Dao接口的代理对象
        IUserDao userDao = session.getMapper(IUserDao.class);
          //创建dao接口实现类使用代理模式：不修改源码就能对已有方法增强

        //5.使用代理对象执行方法
        List<User> users = userDao.findall();
        for(User user:users){
            System.out.println(user);
        }

        //6.释放资源
        session.close();
        in.close();
    }
}
