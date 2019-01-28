package com.freestyle.wenda.dao;

import com.freestyle.wenda.model.LoginTicket;
import com.freestyle.wenda.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface LoginTicketDAO {

    String TABLE_NAME = " login_ticket ";
    String INSERT_FILEDS = " user_id, ticket, expired, status ";
    String SELECT_FIELDS = " id " + INSERT_FILEDS;

    @Insert({"insert into", TABLE_NAME, "(", INSERT_FILEDS, ") " +
            "values(#{userId}, #{ticket}, #{expired}, #{status})"})
    int addLoginTicket(LoginTicket loginTicket);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where ticket = #{ticket}"})
    LoginTicket selectTicket(String ticket);

    @Update({"update ", TABLE_NAME, " set status=#{status} where ticket=#{ticket}"})
    void updateStatus(@Param("ticket") String ticket, @Param("status") int status);
}
