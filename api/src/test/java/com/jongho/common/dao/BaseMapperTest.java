package com.jongho.common.dao;

import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@MybatisTest(properties = "spring.profiles.active=test")
@TestPropertySource(properties = {"spring.profiles.active=test"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = RepositoryTestConfiguration.class)
public class BaseMapperTest {
    @Autowired
    private DataSource dataSource;

    private void setUpTable(String dummyDataSql){
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(dummyDataSql);
        ){
            preparedStatement.execute();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void excuteTruncateTable(String tableName){
        String sql = "TRUNCATE TABLE " + tableName;
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.execute();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void initializeAutoIncrement(String tableName){
        String sql = "ALTER TABLE " + tableName + " AUTO_INCREMENT = 1;";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.execute();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    protected void cleanUpUserTable(){
        excuteTruncateTable("users");
        initializeAutoIncrement("users");
    }
    protected void cleanUpUserNotificationSettingTable(){
        excuteTruncateTable("user_notification_settings");
        initializeAutoIncrement("user_notification_settings");
    }
    protected void cleanUpCategoryTable(){
        excuteTruncateTable("hobby_categories");
        initializeAutoIncrement("hobby_categories");
    }
    protected void cleanUpAuthUserTable(){
        excuteTruncateTable("auth_users");
        initializeAutoIncrement("auth_users");
    }

    protected void cleanUpOpenChatRoomTable(){
        excuteTruncateTable("open_chat_rooms");
        initializeAutoIncrement("open_chat_rooms");
    }

    protected void cleanUpOpenChatRoomUserTable(){
        excuteTruncateTable("open_chat_room_users");
    }

    protected void cleanUpOpenChatRoomMembershipRequestTable(){
        excuteTruncateTable("open_chat_room_membership_requests");
        initializeAutoIncrement("open_chat_room_membership_requests");
    }

    protected void setUpCategoryTable() {
        ClassPathResource resource = new ClassPathResource("setUpDummyData/categoryDummyData.sql");

        try (InputStream inputStream = resource.getInputStream()) {
            String sql = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            setUpTable(sql);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}