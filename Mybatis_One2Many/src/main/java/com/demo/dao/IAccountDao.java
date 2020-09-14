package com.demo.dao;

import com.demo.domain.Account;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface IAccountDao {
    @Select("select * from account")
    @Results(id="accountMap",value = {
            @Result(id=true,column = "id",property = "id"), //id=true 主键
            @Result(column = "uid",property = "uid"),
            @Result(column = "money",property = "money"),
            @Result(property = "user",column = "uid",one=@One(select="com.demo.dao.IUserDao.findById",fetchType =FetchType.EAGER))
    })
    List<Account> findall();
    /*One2Many：一个人可以有多个账号 但一个账号只对应一个人 这里账号对人是一对一（） 人对账号是一对多
    fetchtype：延迟查询LAZY：查询结果为多条 立即查询EAGER：查询结果为1条*/

    @Select("select * from account where uid = #{userId}")
    @ResultMap(value={"accountMap"})
    List<Account> findByUid(Integer userId);

}
