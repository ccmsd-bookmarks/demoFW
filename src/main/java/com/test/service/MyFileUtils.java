package com.test.service;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;

/**
 * File utility
 * 
 * @author aos
 *
 */
public class MyFileUtils {

    /**
     * Get list of all files in directory, recursively if need be
     * 
     * @param fileExtentionWildCard
     *            - File extension wild card
     * 
     * @return {@link IOFileFilter}
     */
    public static IOFileFilter getAllFilesWithExtention(String fileExtentionWildCard) {
        return FileFilterUtils.and(FileFilterUtils.fileFileFilter(), new WildcardFileFilter(fileExtentionWildCard));
    }

    /**
     * @param path
     * @return the file name with extension
     */
    public static String getFilenameWithExtention(String path) {
        return FilenameUtils.getName(path);
    }
}
