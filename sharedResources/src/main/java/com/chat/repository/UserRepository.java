package com.chat.repository;

import java.util.List;

public interface UserRepository {

    int getIdByPhoneNumber(String PhoneNumber);
    List<Integer> getIDsByPhoneNumber(List<String> phoneNumbers);






}
