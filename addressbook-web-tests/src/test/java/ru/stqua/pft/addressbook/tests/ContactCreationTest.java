package ru.stqua.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqua.pft.addressbook.model.ContactData;
import ru.stqua.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class ContactCreationTest extends TestBase{

  @Test
  public void testContactCreation() throws Exception {
    app.getNavigationHelper().goToGroupPage();
    if (! app.getGroupHelper().isThereAGroup()){
      app.getGroupHelper().createGroup(new GroupData("test1", "test2", "test3"));
    }
    app.getNavigationHelper().goToContactPage();
    List<ContactData> before = app.getContactHelper().getContactList();
    ContactData contact = new ContactData("TestName", "TestMiddleName",
            "test1","test1", "TestNickname", "Title Test",
            "Bookovsky", "196190 Saint-Petersburg, Moskovsky pr. 163",
            "88123456456", "+78945634", "testaddress@mail.ru");
    app.getContactHelper().createContact(contact);
    app.getNavigationHelper().goToContactPage();
    List <ContactData> after = app.getContactHelper().getContactList();

    int max = after.stream().max((o1,o2)->Integer.compare(o1.getId(),o2.getId())).get().getId();
    contact.setId(max);
    before.add(contact);

    Comparator<? super ContactData> byId = (c1,c2)->Integer.compare(c1.getId(),c2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(before,after);
  }
}
