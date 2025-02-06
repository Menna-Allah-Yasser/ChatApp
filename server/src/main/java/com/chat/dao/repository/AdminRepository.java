package com.chat.dao.repository;

import java.util.List;
import com.chat.entity.Admin;

public interface AdminRepository {

    boolean addAdmin(Admin admin);

    Admin getAdminById(int adminId);

    List<Admin> getAllAdmins();

    boolean updateAdmin(Admin admin);

    boolean deleteAdmin(int adminId);
}
