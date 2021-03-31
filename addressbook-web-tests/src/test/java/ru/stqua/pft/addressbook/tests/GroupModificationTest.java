package ru.stqua.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqua.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

public class GroupModificationTest extends TestBase{

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().groupPage();
    if (app.group().list().size()==0) {
      app.group().create(new GroupData().withName("test name").withHeader("header"));
    }
  }

  @Test
  public void testGroupModification() throws Exception {
    List<GroupData> before = app.group().list();
    GroupData group = new GroupData().withId(before.get(before.size()-1).getId()).withName("test1")
            .withHeader("test2").withFooter("test3");
    int index = before.size()-1;
    app.group().modify(group, index);
    List<GroupData> after = app.group().list();
    Assert.assertEquals(after.size(), before.size());

    before.remove(index);
    before.add(group);
    Comparator<? super GroupData> byId = (g1, g2)->Integer.compare(g1.getId(),g2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(before,after);
  }




}
