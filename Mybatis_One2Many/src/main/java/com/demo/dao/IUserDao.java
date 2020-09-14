package com.demo.dao;
import com.demo.domain.Account;
import com.demo.domain.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

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
    @Results(id="userMap",value = {
            @Result(id=true,column = "id",property = "userId"), //id=true 主键
            @Result(column = "username",property = "userName"),
            @Result(column = "birthday",property = "userBirthday"),
            @Result(column = "sex",property = "userSex"),
            @Result(column = "address",property = "userAddress"),
            @Result(property = "accounts",column = "id",many=@Many(select = "com.demo.dao.IAccountDao.findByUid",fetchType = FetchType.LAZY))
    })
    List<User> findall();
    /*@Results 和 @Result 解决dao属性和数据库属性命名不一致问题
      若没有这里的映射关系则查询后打印的结果如下，部分属性未能被封装:
    User{userId=null, userName='化学系杭', userBirthday=null, userSex='null', userAddress='null'}
    User{userId=null, userName='化学系杭', userBirthday=null, userSex='null', userAddress='null'}
     */


    //查询部分
    @Select("select * from user where id=#{id}")
    @ResultMap(value={"userMap"})
    User findById(Integer userId);
    /*@ResultMap相当于复制了一边上面的@Results
      若此处无@ResultMap 查询打印的结果同上，部分属性未能被封装
      上一句也可直接写为： @ResultMap("userMap")
      value在注解中如果是唯一的一个属性则可以省略 若还有别的则要加上（参照上满@Result）
      若集合元素中只有一个元素 则可以省略{}
    */

    //根据名字模糊查询
    @Select("select * from user where username like '%${username}%'")
    @ResultMap(value={"userMap"})
    List<User> findUserByName(String username);

    @Select("select * from account where uid = #{userId}")
    @ResultMap(value={"userMap"})
    List<Account> findAccountByUid(Integer userId);
}