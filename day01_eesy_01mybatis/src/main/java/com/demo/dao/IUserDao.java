package com.demo.dao;
import com.demo.domain.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/*用户的持久层接口*/
/*注解4种：@SELECT @INSERT @UPDATE @DELETE*/
/*此处用mybatis方法2：注解。 @Select一句话代替了原本方法1：xml中
                           要在resources文件夹底下新建com.demo.dao.IUserDao.xml创建映射关系的一步。
    注解更为简便，但也存在难以维护的缺点。
    IUserDao.xml和注解方法二选一，若共存，即使在SqlMapConfig.xml不用xml也没配上xml也会报错。*/
public interface IUserDao {
    //查询所有
    @Select("select * from user")
    List<User> findall();

    @Insert("insert into user(username,birthday,sex,address) values(#{username},#{birthday},#{sex},#{address})")
    void saveUser(User user);

    @Update("update user set username=#{username},birthday=#{birthday},sex=#{sex},address=#{address} where id=#{id}")
    void updateUser(User user);

    @Delete("delete from user where id=#{id}")
    void deleteUser(Integer userId);

    //查询部分
    @Select("select * from user where id=#{id}")
    User findById(Integer userId);

    //根据名字模糊查询
    @Select("select * from user where username like '%${username}%'")
    List<User> findUserByName(String username);

    //查询总用户数量
    @Select("select count(*) from user")
    int findTotalUser();
}