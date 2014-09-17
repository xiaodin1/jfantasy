package com.fantasy.framework;

import com.fantasy.file.FileItem;
import com.fantasy.file.FileItemSelector;
import com.fantasy.file.FileManager;
import com.fantasy.file.bean.FileDetail;
import com.fantasy.file.manager.LocalFileManager;
import com.fantasy.file.service.FileService;
import com.fantasy.framework.util.common.MessageDigestUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml" })
public class ImageRecovery {

    @Resource
    private FileService fileService;

    @Test
    public void recovery() throws IOException {
        FileManager fileManager = new LocalFileManager("/Users/lmf/Downloads/rex网站图片/");
        final FileManager rexFiles = new LocalFileManager("/Users/lmf/Downloads/rex网站图片-files");
        fileManager.listFiles(new FileItemSelector() {
            @Override
            public boolean includeFile(FileItem fileItem) {
                if(!fileItem.isDirectory()) {
                    try {
                        File file = new File("/Users/lmf/Downloads/rex网站图片" + fileItem.getAbsolutePath());
                        String md5 = MessageDigestUtil.getInstance().get(file);
                        FileDetail fileDetail = fileService.findUniqueByMd5(md5,"haolue-upload");
                        if(fileDetail != null){
                            System.out.println(fileDetail.getRealPath());
                            rexFiles.writeFile(fileDetail.getRealPath(),file);
                        }else {
                            System.err.println(fileItem.getAbsolutePath() + "=>" + md5);
                        }
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
                return true;
            }

            @Override
            public boolean traverseDescendents(FileItem fileItem) {
                System.out.println(fileItem.getAbsolutePath());
                return true;
            }
        });
        //System.out.println(fileService);
    }

}
