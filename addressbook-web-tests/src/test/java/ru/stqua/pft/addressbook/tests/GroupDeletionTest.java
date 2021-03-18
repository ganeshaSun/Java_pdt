package ru.stqua.pft.addressbook.tests;

import org.testng.annotations.*;


public class GroupDeletionTest extends TestBase{

  @Test
  public void testGroupDeletion() throws Exception {
    app.getNavigationHelper().goToGroupPage();
    app.getGroupHelper().selectGroup();
    app.getGroupHelper().deleteSelectedGroups();
    app.getGroupHelper().returnToGroupPage();
  }


}