package com.trailblazers.freewheelers.mappers;

import com.trailblazers.freewheelers.model.Account;

import com.trailblazers.freewheelers.model.Country;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface AccountMapper {

    @Insert(
        "INSERT INTO account (account_name, email_address, password, country_id, phone_number, enabled) " +
        "VALUES (#{account_name}, #{emailAddress}, #{password}, #{country.countryId}, #{phoneNumber}, #{enabled})"
    )
    @Options(keyProperty = "account_id", useGeneratedKeys = true)
    Integer insert(Account account);

    @Select(
        "SELECT account_id, account_name, email_address, password, country_id, phone_number, enabled " +
        "FROM account " +
        "WHERE account_id = #{account_id} "
    )
    @Results(value = {
            @Result(property="account_id"),
            @Result(property="account_name"),
            @Result(property="emailAddress", column="email_address"),
            @Result(property="password"),
            @Result(property ="country", column="country_id", javaType = Country.class,
                    one = @One(select = "com.trailblazers.freewheelers.mappers.CountryMapper.getById")),
            @Result(property="phoneNumber", column="phone_number"),
            @Result(property="enabled")
    })
    Account getById(Long account_id);

    @Select(
        "SELECT account_id, account_name, email_address, password, country_id, phone_number, enabled " +
        "FROM account " +
        "WHERE account_name = #{account_name} " +
        "LIMIT 1 "

    )
    @Results(value = {
            @Result(property="account_id"),
            @Result(property="account_name"),
            @Result(property="emailAddress", column="email_address"),
            @Result(property="password"),
            @Result(property="country", column = "country_id", javaType = Country.class,
                    one = @One(select = "com.trailblazers.freewheelers.mappers.CountryMapper.getById")),
            @Result(property="phoneNumber", column="phone_number"),
            @Result(property="enabled")
    })
    Account getByName(String accountName);

    @Update(
        "UPDATE account " +
        "SET account_name=#{account_name}, email_address=#{emailAddress}, country_id=#{country.countryId}, phone_number=#{phoneNumber}, enabled=#{enabled} " +
        "WHERE account_id=#{account_id}"
    )
    void update(Account account);

    @Select(
        "SELECT account_id, account_name, email_address, password, country_id, phone_number, enabled " +
        " FROM account "
    )
    @Results(value = {
            @Result(property="account_id"),
            @Result(property="account_name"),
            @Result(property="emailAddress", column="email_address"),
            @Result(property="password"),
            @Result(property="country", column = "country_id", javaType = Country.class,
                    one = @One(select = "com.trailblazers.freewheelers.mappers.CountryMapper.getById")),
            @Result(property="phoneNumber", column="phone_number"),
            @Result(property="enabled")
    })
    public List<Account> findAll();

    @Delete(
        "DELETE FROM account WHERE account_id = #{account_id}"
    )
    @Options(flushCache = true)
    void delete(Account account);


}
