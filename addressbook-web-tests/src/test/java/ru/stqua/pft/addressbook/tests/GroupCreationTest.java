package ru.stqua.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import ru.stqua.pft.addressbook.model.GroupData;

import java.util.List;

public class GroupCreationTest extends TestBase{
  @Test
  public void testGroupCreation() throws Exception {
    app.getNavigationHelper().goToGroupPage();
    List <GroupData> before = app.getGroupHelper().getGroupList();
    app.getGroupHelper().createGroup(new GroupData("test1", "test2", "test3"));
    List <GroupData> after = app.getGroupHelper().getGroupList();
    Assert.assertEquals(after.size(),before.size()+1);
  }


}
