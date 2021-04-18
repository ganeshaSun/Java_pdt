package ru.stqua.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.stqua.pft.addressbook.model.ContactData;
import ru.stqua.pft.addressbook.model.Contacts;
import ru.stqua.pft.addressbook.appmanager.ApplicationManager;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


public class ContactAddToGroupTest extends TestBase {

  private SessionFactory sessionFactory;

  @BeforeClass
  public void setUp() throws Exception {
    // A SessionFactory is set up once for an application!
    final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure() // configures settings from hibernate.cfg.xml
            .build();
    try {
      sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
    }
    catch (Exception e) {
      e.printStackTrace();
      // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
      // so destroy it manually.
      StandardServiceRegistryBuilder.destroy( registry );
    }
  }

  @Test
  public void testContactAddToGroup() {
    Session session = sessionFactory.openSession();
    session.beginTransaction();

    Contacts before = app.db().contacts();
    ContactData contact = before.iterator().next();
    int id = contact.getId();

    ContactData beforeContact = (ContactData) session.createQuery( "from ContactData where id="+contact.getId()).getSingleResult();
    System.out.println(beforeContact);
    System.out.println(beforeContact.getGroups());

    session.getTransaction().commit();
    session.close();

    app.goTo().contactPage();
    app.contact().addGroup(contact);

    session = sessionFactory.openSession();
    session.beginTransaction();

    ContactData afterContact = (ContactData) session.createQuery( "from ContactData where id="+contact.getId()).getSingleResult();
    System.out.println("After " + afterContact);
    System.out.println(afterContact.getGroups());

    session.getTransaction().commit();
    session.close();





/*    assertThat(app.contact().count(), equalTo(before.size()));
    Contacts after = app.db().contacts();
    assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
    verifyContactListInUI();*/
  }
}
