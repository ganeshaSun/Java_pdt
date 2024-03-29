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

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


public class ContactAddToGroupTest extends TestBase {

  private SessionFactory sessionFactory;
  private GroupData groupForAdding;
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

  public void ensureNotEmptyLists(){
    app.goTo().contactPage();
    if (app.db().contacts().size()==0) {
      app.goTo().groupPage();
      if (app.db().groups().size()==0){
        app.group().create(new GroupData().withName("test 1").withHeader("head").withFooter("footer"));
      }
      app.goTo().contactPage();
      app.contact().create(new ContactData().withFirstName("TestName").withMiddlename("TestMiddleName")
              .withLastname("Last").withAddress("196190 Saint-Petersburg, Moskovsky pr. 163").withCompany("Bookovsky")
              .withHomePhone("88123456456").withMobilePhone("+78945634").withWorkPhone("123123").withEmail("testaddress@mail.ru"));
    }
  }

  @BeforeMethod
  public void ensurePreconditions() {
    ensureNotEmptyLists();

    Session session = openSessionDb();
    List<ContactData> contactList = session.createQuery("from ContactData").list();
    List<GroupData> allGroupsList = session.createQuery("from GroupData").list();
    for (ContactData contact : contactList) {
      if (contact.getGroups().size() == 0) {
        groupForAdding = allGroupsList.iterator().next();
        contactAddToGroup = contact;
        break;
      }
      if (contact.getGroups().size() != allGroupsList.size()) {
        contactAddToGroup = contact;
        for (GroupData group : allGroupsList) {
          if (contact.getGroups().stream().noneMatch(group::equals)) {
            groupForAdding = group;
            break;
          }
        }
      }
      if (contact.getGroups().size() == allGroupsList.size()) {
        groupForAdding = newGroupCreation();
        app.goTo().homePage();
        contactAddToGroup = contact;
      }
    }
    closeSession(session);
  }

  public GroupData newGroupCreation() {
    app.goTo().groupPage();
    GroupData newGroup = new GroupData().withName("newGroup!!").withHeader("newGroupHeader");
    app.group().create(newGroup);
    newGroup = newGroup.withId(app.db().groups().stream()
            .mapToInt((g) -> g.getId()).max().getAsInt());
    return newGroup;
  }

  public void closeSession(Session session) {
    session.getTransaction().commit();
    session.close();
  }

  public Session openSessionDb() {
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    return session;
  }

  public ContactData contactFromDbById(Session session) {
    return (ContactData) session.createQuery("from ContactData where id=" +
            contactAddToGroup.getId()).getSingleResult();
  }

  @Test
  public void testContactAddToGroup() {
    Session session = openSessionDb();

    ContactData beforeContact = contactFromDbById(session);
    Groups beforeGroups = beforeContact.getGroups();
    closeSession(session);

    app.goTo().homePage();
    app.contact().addContactToGroup(contactAddToGroup, groupForAdding);

    session = sessionFactory.openSession();
    session.beginTransaction();
    ContactData afterContact = contactFromDbById(session);
    Groups afterGroups = afterContact.getGroups();
    closeSession(session);

    assertThat(afterContact, equalTo(beforeContact));
    assertThat(afterGroups.size(), equalTo(beforeGroups.size() + 1));
    verifyContactListInUI();
  }


}


