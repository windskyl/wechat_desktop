package com.rc.db.service;

import com.rc.db.dao.ContactsUserDao;
import com.rc.db.model.Contacts;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * Created by song on 08/06/2017.
 */
public class ContactsService extends BasicService<ContactsUserDao, Contacts>
{
    public ContactsService(SqlSession session)
    {
        dao = new ContactsUserDao(session);
        super.setDao(dao);
    }

    public int insertOrUpdate(Contacts contacts)
    {
        if (exist(contacts.getUsername()))
        {
            return update(contacts);
        }else
        {
            return insert(contacts);
        }
    }

    public int deleteByUsername(String name)
    {
        return dao.deleteByUsername(name);
    }

    public Contacts findByUsername(String username)
    {
        List list = dao.find("username", username);
        if (list != null && list.size() > 0)
        {
            return (Contacts) list.get(0);
        }

        return null;
    }

    public List<Contacts> searchByUsernameOrName(String username, String name)
    {
        return dao.searchByUsernameOrName(username, name);
    }
}







