package org.crown.common.utils.file;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.crown.common.utils.DateUtils;
import org.crown.common.utils.Md5Utils;
import org.crown.common.utils.StringUtils;
import org.crown.framework.exception.Crown2Exception;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

/**
 * File upload tools
 *
 * @author Crown
 */
@Slf4j
public class FileUploadUtils {

    /**
     * Default size 50M
     */
    public static final long DEFAULT_MAX_SIZE = 50 * 1024 * 1024;

    /**
     * Default file name length 100
     */
    public static final int DEFAULT_FILE_NAME_LENGTH = 100;

    private static int counter = 0;

    /**
     * File Upload
     *
     * @param baseDir          Relative application base directory
     * @param file             Uploaded file
     * @param allowedExtension Upload file type
     * @return Return the name of the uploaded file
     * @throws IOException For example, when reading and writing files are wrong
     */
    public static String upload(String baseDir, MultipartFile file, String[] allowedExtension) throws IOException {
        int fileNamelength = Objects.requireNonNull(file.getOriginalFilename()).length();
        if (fileNamelength > FileUploadUtils.DEFAULT_FILE_NAME_LENGTH) {
            throw new Crown2Exception(HttpServletResponse.SC_BAD_REQUEST, "The file name is too long. The maximum length of the file name is:" + DEFAULT_FILE_NAME_LENGTH);
        }

        assertAllowed(file, allowedExtension);
        String fileName = extractFilename(file);
        File desc = getAbsoluteFile(baseDir, fileName);
        BufferedOutputStream stream = null;
        try {
            byte[] bytes = file.getBytes();
            stream = new BufferedOutputStream(new FileOutputStream(desc));
            stream.write(bytes);
        } catch (Exception e) {
            throw new Crown2Exception(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "There was an error uploading the file!", e);
        } finally {
            IOUtils.closeQuietly(stream);
        }
        return fileName;
    }

    /**
     * Encoding file name
     */
    public static String extractFilename(MultipartFile file) {
        String filename = file.getOriginalFilename();
        String extension = getExtension(file);
        filename = DateUtils.datePath() + "/" + encodingFilename(filename) + "." + extension;
        return filename;
    }

    private static File getAbsoluteFile(String uploadDir, String filename) throws IOException {
        File desc = new File(uploadDir + File.separator + filename);

        if (!desc.getParentFile().exists()) {
            //noinspection ResultOfMethodCallIgnored
            desc.getParentFile().mkdirs();
        }
        if (!desc.exists()) {
            //noinspection ResultOfMethodCallIgnored
            desc.createNewFile();
        }
        return desc;
    }

    /**
     * Encoding file name
     */
    private static String encodingFilename(String filename) {
        filename = filename.replace("_", " ");
        filename = Md5Utils.hash(filename + System.nanoTime() + counter++);
        return filename;
    }

    /**
     * File size check
     *
     * @param file Uploaded file
     * @return
     */
    public static void assertAllowed(MultipartFile file, String[] allowedExtension) {
        long size = file.getSize();
        if (size > DEFAULT_MAX_SIZE) {
            throw new Crown2Exception(HttpServletResponse.SC_BAD_REQUEST, "The uploaded file size exceeds the limit file size! The maximum allowed file size is:" + DEFAULT_MAX_SIZE / 1024 / 1024 + "MB！");
        }

        String extension = getExtension(file);
        if (allowedExtension != null && !isAllowedExtension(extension, allowedExtension)) {
            throw new Crown2Exception(HttpServletResponse.SC_BAD_REQUEST, "This type of file upload is not supported. The current file type is:[" + extension + "]！The allowed file types are:" + Arrays.toString(allowedExtension));
        }

    }

    /**
     * Determine whether the MIME type is an allowed MIME type
     *
     * @param extension
     * @param allowedExtension
     * @return
     */
    public static boolean isAllowedExtension(String extension, String[] allowedExtension) {
        for (String str : allowedExtension) {
            if (str.equalsIgnoreCase(extension)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the suffix of the file name
     *
     * @param file Form file
     * @return Suffix
     */
    public static String getExtension(MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (StringUtils.isEmpty(extension)) {
            extension = MimeTypes.getExtension(Objects.requireNonNull(file.getContentType()));
        }
        return extension;
    }

}
