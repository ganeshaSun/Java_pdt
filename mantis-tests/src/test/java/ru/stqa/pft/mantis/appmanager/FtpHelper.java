package ru.stqa.pft.mantis.appmanager;
import org.apache.commons.net.ftp.FTPClient;
import java.io.File;
import java.io.FileInputStream;

public class FtpHelper {


  private final ApplicationManager app;
  private FtpClient ftp;

  public FtpHelper(ApplicationManager app){
    this.app = app;

    ftp = new FtpClient();
  }

  public void upload(File file, String target, String backup){
    ftp.connect(app.getProperty("ftp.host"));
    ftp.login(app.getProperty("ftp.login"),app.getProperty("ftp.password"));
    ftp.deleteFile(backup);
    ftp.rename(target,backup);
    ftp.enterLocalPassiveMode();
    ftp.storeFile(target, new FileInputStream(file));
    ftp.disconnect();
  }

  public void restore(String backup, String target){
    ftp.connection(app.getProperty("ftp.host"));
    ftp.login(app.getProperty("ftp.login"), app.getProperty("ftp.password"));
    ftp.deleteFile(target);
    ftp.rename(backup,target);
    ftp.disconnect();
  }
}
