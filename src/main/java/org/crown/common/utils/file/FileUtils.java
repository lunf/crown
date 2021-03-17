package org.crown.common.utils.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;

import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * File processing tools
 *
 * @author Crown
 */
@Slf4j
public class FileUtils {

    public static final String FILENAME_PATTERN = "[a-zA-Z0-9_\\-|.\\u4e00-\\u9fa5]+";

    /**
     * Output the byte array of the specified file
     *
     * @param filePath file path
     * @param os       Output stream
     * @return
     */
    public static void writeBytes(String filePath, OutputStream os) throws IOException {
        FileInputStream fis = null;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                throw new FileNotFoundException(filePath);
            }
            fis = new FileInputStream(file);
            byte[] b = new byte[1024];
            int length;
            while ((length = fis.read(b)) > 0) {
                os.write(b, 0, length);
            }
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * Delete Files
     *
     * @param filePath file
     * @return
     */
    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        // Delete if the path is a file and not empty
        if (file.isFile() && file.exists()) {
            return file.delete();
        }
        return false;
    }

    /**
     * File name verification
     *
     * @param filename file name
     * @return true normal false illegal
     */
    public static boolean isValidFilename(String filename) {
        return filename.matches(FILENAME_PATTERN);
    }

    /**
     * Download file name re-encoding
     *
     * @param request  Request object
     * @param fileName file name
     * @return File name after encoding
     */
    public static String setFileDownloadHeader(HttpServletRequest request, String fileName) throws UnsupportedEncodingException {
        final String agent = request.getHeader("USER-AGENT");
        String filename = fileName;
        if (agent.contains("MSIE")) {
            // IE browser
            filename = URLEncoder.encode(filename, "utf-8");
            filename = filename.replace("+", " ");
        } else if (agent.contains("Firefox")) {
            // Firefox browser
            filename = new String(fileName.getBytes(), "ISO8859-1");
        } else if (agent.contains("Chrome")) {
            // google browser
            filename = URLEncoder.encode(filename, "utf-8");
        } else {
            //Other browsers
            filename = URLEncoder.encode(filename, "utf-8");
        }
        return filename;
    }

    /**
     * Create a directory
     *
     * @param dirPath
     * @return
     */
    public static boolean makeDir(String dirPath) {
        return makeDir(new File(dirPath));
    }

    /**
     * Create a directory
     *
     * @param file
     * @return
     */
    public static boolean makeDir(File file) {
        if (!file.exists() && !file.isDirectory()) {
            return file.mkdirs();
        } else {
            return true;
        }
    }

    /**
     * Get the current project path
     *
     * @return
     */
    public static String getAbsPathOfProject(HttpServletRequest request) {
        return request.getSession().getServletContext().getRealPath("//");
    }

    /**
     * Get the project file stream path
     *
     * @return
     */
    public static InputStream getResourceAsStreamOfProject(HttpServletRequest request, String path) {
        return request.getSession().getServletContext().getResourceAsStream(path);
    }

    /**
     * Get the file extension
     *
     * @param fileName file name
     * @return
     */
    public static String getFilePrefix(String fileName) {
        if (null != fileName && fileName.contains(".")) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        }
        return null;
    }

    /**
     * Get the upper-level directory of the file
     *
     * @param path file path
     * @return
     */
    public static String getFileParentPath(String path) {
        File file = new File(path);
        if (file.exists() && !file.isDirectory()) {
            return path.substring(0, path.lastIndexOf("/") + 1);
        }
        return null;
    }

    /**
     * How to delete files (only applicable to delete files)
     *
     * @param files
     * @author Caratacus
     */
    public static void delFiles(List<File> files) {
        if (files != null && !files.isEmpty()) {
            for (File dir : files) {
                if (dir == null || !dir.exists()) {
                    return;
                } else {
                    dir.delete();
                }
            }
        }
    }

    /**
     * Delete all files in the directory (including directories and files)
     *
     * @param dir
     */
    public static void delFile(File dir) {
        try {
            if (dir != null) {
                if (dir.isDirectory()) {
                    File[] files = dir.listFiles();
                    if (ArrayUtils.isNotEmpty(files)) {
                        for (File file : files) {
                            if (file.isDirectory()) {
                                File[] listFiles = file.listFiles();
                                if (ArrayUtils.isNotEmpty(listFiles)) {
                                    delFile(file);
                                } else {
                                    file.delete();
                                }
                            } else {
                                file.delete();
                            }
                        }
                    }
                }
                dir.delete();
            }
        } catch (Exception e) {
            log.error("Path: " + dir.getAbsolutePath() + " , Delete File Result : Error");
        }

    }

    /**
     * http download file
     *
     * @param httpUrl
     * @param dir      File Directory
     * @param fileName file name
     * @return
     */
    public static boolean httpDownload(String httpUrl, String dir, String fileName) {
        int byteRead;
        URL url;
        log.info("File is downloading,PATH:" + httpUrl);
        try {
            url = new URL(httpUrl);
        } catch (MalformedURLException e) {
            log.warn("Warn: MalformedURLException on httpDownload.  Cause:" + e);
            return false;
        }
        FileOutputStream fileOutputStream = null;
        URLConnection connection = null;
        try {
            connection = url.openConnection();
            // Set the timeout to 5 seconds
            connection.setConnectTimeout(5 * 1000);
            // Prevent the blocking program from crawling and returning 403 errors
            connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            InputStream inputStream = connection.getInputStream();
            makeDir(dir);
            fileOutputStream = new FileOutputStream(dir + File.separator + fileName);
            byte[] buffer = new byte[8192];
            while ((byteRead = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, byteRead);
            }
            return true;
        } catch (FileNotFoundException e) {
            log.warn("Warn: FileNotFoundException on httpDownload.  Cause:" + e);
            return false;
        } catch (IOException e) {
            log.warn("Warn: IOException on httpDownload.  Cause:" + e);
            return false;
        } finally {
            IOUtils.close(connection);
            IOUtils.closeQuietly(fileOutputStream);
        }
    }

    /**
     * Calculate file size (applicable to calculate file and folder size)
     *
     * @param file file
     * @return Return long type byte (B)
     */
    public static long size(File file) {
        if (file.exists()) {
            return file.length();
        }
        return 0L;
    }

    /**
     * Calculate file size
     *
     * @param filePath file path
     * @return Return long type byte (B)
     */
    public static long size(String filePath) {
        return size(new File(filePath));
    }

    /**
     * 拷贝文件方法
     *
     * @param oldPath String Original file path such as：c:/fqf.txt
     * @param newPath String Copy the path such as：f:/fqf.txt
     */
    public static void copyFile(String oldPath, String newPath) {
        InputStream inStream = null;
        FileOutputStream outputStream = null;
        try {
            int byteread;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { // When the file exists
                inStream = new FileInputStream(oldPath); // Read in the original file
                outputStream = new FileOutputStream(newPath);
                byte[] buffer = new byte[8192];
                while ((byteread = inStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            log.warn("Warn: Exception on copyFile.  Cause:" + e);
        } finally {
            IOUtils.closeQuietly(inStream, outputStream);
        }
    }

    /**
     * Read a text file and return a string
     *
     * @param filePath
     * @return
     */
    public static String readFileString(String filePath) {
        File file = new File(filePath);
        BufferedReader reader = null;
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
            reader = new BufferedReader(isr);
            StringBuilder builder = new StringBuilder(128);
            String tempString;
            while ((tempString = reader.readLine()) != null) {
                builder.append(tempString);
            }
            return builder.toString();
        } catch (IOException e) {
            log.warn("Warn: Exception on getStringByFile.  Cause:" + e);
        } finally {
            IOUtils.closeQuietly(reader);
        }
        return null;
    }
}
