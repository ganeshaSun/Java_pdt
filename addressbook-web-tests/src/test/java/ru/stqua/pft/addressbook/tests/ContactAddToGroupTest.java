package ru.stqua.pft.addressbook.tests;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqua.pft.addressbook.model.ContactData;
import ru.stqua.pft.addressbook.model.Contacts;
import ru.stqua.pft.addressbook.model.GroupData;
import ru.stqua.pft.addressbook.model.Groups;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


public class ContactAddToGroupTest extends TestBase {

  private SessionFactory sessionFactory;
  private List<GroupData> groupForAdding =new ArrayList<GroupData>();
  private ContactData contactAddToGroup;

  @BeforeClass
  protected void setUpSession() throws Exception {
    // A SessionFactory is set up once for an application!
    final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure() // configures settings from hibernate.cfg.xml
            .build();
    try {
      sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    } catch (Exception e) {
      e.printStackTrace();
      // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
      // so destroy it manually.
      StandardServiceRegistryBuilder.destroy(registry);
    }
  }

  @BeforeMethod
  public void ensurePreconditions() {
    boolean needToCreateGroup = true;

    Session session = sessionFactory.openSession();
    session.beginTransaction();
    List<ContactData> contactList = session.createQuery("from ContactData").list();
    List<GroupData> allGroupsList = session.createQuery("from GroupData").list();
    for (ContactData contact : contactList) {
      if (contact.getGroups().size()==0){
        GroupData gr = allGroupsList.iterator().next();
        groupForAdding.add(gr);
      }
      if (contact.getGroups().size() != allGroupsList.size()) {
        needToCreateGroup = false;
        contactAddToGroup = contact;
        for (GroupData g : contact.getGroups()) {
          for (GroupData group : allGroupsList) {
            if (group.getId() != g.getId()) {
              groupForAdding.add(group);
            }
          }
          break;
        }

      }
      if (needToCreateGroup == true) {
        app.goTo().groupPage();
        GroupData newGroup = new GroupData().withName("newGroup").withHeader("newGroupHeader");
        app.group().create(newGroup);
        List<GroupData> groupsLstwithNew = session.createQuery("from GroupData").list();
        groupForAdding.add(newGroup.withId(groupsLstwithNew.stream().mapToInt((g) -> g.getId()).max().getAsInt()));

        app.goTo().contactPage();
        Contacts c = app.db().contacts();
        contactAddToGroup = c.iterator().next();
      }
      session.getTransaction().commit();
      session.close();
    }
  }

  @Test
  public void testContactAddToGroup() {
    Session session = sessionFactory.openSession();
    session.beginTransaction();

    ContactData beforeContact = (ContactData) session.createQuery("from ContactData where id=" +
            contactAddToGroup.getId()).getSingleResult();
    Groups beforeGroups = beforeContact.getGroups();
    System.out.println("Before " + beforeContact);
    System.out.println("groups linked before" + beforeContact.getGroups());
    session.getTransaction().commit();
    session.close();

    app.goTo().contactPage();
    app.contact().addGroup(contactAddToGroup, groupForAdding);

    session = sessionFactory.openSession();
    session.beginTransaction();
    ContactData afterContact = (ContactData) session.createQuery("from ContactData where id=" +
            contactAddToGroup.getId()).getSingleResult();
    Groups afterGroups = afterContact.getGroups();
    System.out.println("After " + afterContact);
    System.out.println("groups linked after" + afterContact.getGroups());
    session.getTransaction().commit();
    session.close();

    assertThat(afterContact, equalTo(beforeContact));
    assertThat(afterGroups.size(), equalTo(beforeGroups.size() + 1));
    verifyContactListInUI();
  }

}


