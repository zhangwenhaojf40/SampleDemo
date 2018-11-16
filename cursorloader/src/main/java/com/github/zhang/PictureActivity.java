package com.github.zhang;

import android.Manifest;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PictureActivity extends BaseActivity {

    private RecyclerView recyclerView;


    // load type
    public static final int TYPE_IMAGE = 1;
    public static final int TYPE_VIDEO = 2;
    private int type = TYPE_IMAGE;

    private final static String[] IMAGE_PROJECTION = {
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.DATE_ADDED,
            MediaStore.Images.Media._ID};


    private final static String[] VIDEO_PROJECTION = {
            MediaStore.Video.Media.DATA,
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media.DATE_ADDED,
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.DURATION,
    };
    private AlbumDirectoryAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pictrue);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        adapter = new AlbumDirectoryAdapter(PictureActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        requestPermiss(1);

    }

    @Override
    protected void initData() {
        LoaderManager.getInstance(this).initLoader(1, null, new LoaderManager.LoaderCallbacks<Cursor>() {
            @NonNull
            @Override
            public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle bundle) {
                CursorLoader cursorLoader = null;
                if (id == TYPE_IMAGE) {
                    cursorLoader = new CursorLoader(
                            PictureActivity.this, MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            IMAGE_PROJECTION, MediaStore.Images.Media.MIME_TYPE + "=? or "
                            + MediaStore.Images.Media.MIME_TYPE + "=?" + " or "
                            + MediaStore.Images.Media.MIME_TYPE + "=?",
                            new String[]{"image/jpeg", "image/png", "image/gif"}, IMAGE_PROJECTION[2] + " DESC");
                } else if (id == TYPE_VIDEO) {
                    cursorLoader = new CursorLoader(
                            PictureActivity.this, MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                            VIDEO_PROJECTION, null, null, VIDEO_PROJECTION[2] + " DESC");
                }
                return cursorLoader;
            }

            @Override
            public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
                try {
                    ArrayList<LocalMediaFolder> imageFolders = new ArrayList<LocalMediaFolder>();
                    LocalMediaFolder allImageFolder = new LocalMediaFolder();
                    List<LocalMedia> allImages = new ArrayList<LocalMedia>();
                    if (data != null) {
                        int count = data.getCount();
                        if (count > 0) {
                            data.moveToFirst();
                            do {

                                String path = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[0]));
                                String name = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[1]));
                                // 如原图路径不存在或者路径存在但文件不存在,就结束当前循环
                                if (TextUtils.isEmpty(path) || !new File(path).exists()) {
                                    continue;
                                }
                                long dateTime = data.getLong(data.getColumnIndexOrThrow(IMAGE_PROJECTION[2]));
                                int duration = (type == TYPE_VIDEO ? data.getInt(data.getColumnIndexOrThrow(VIDEO_PROJECTION[4])) : 0);
                                LocalMedia image = new LocalMedia(path, dateTime, duration, type);
                                LocalMediaFolder folder = getImageFolder(path, imageFolders);
                                Log.i("FolderName", folder.getName());
                                folder.getImages().add(image);
                                folder.setImageNum(folder.getImageNum() + 1);
                                folder.setType(type);
                                allImages.add(image);
                                allImageFolder.setType(type);
                                allImageFolder.setImageNum(allImageFolder.getImageNum() + 1);
                            } while (data.moveToNext());

                            if (allImages.size() > 0) {
                                String title = "";
                                switch (type) {
                                    case TYPE_VIDEO:
                                        title = "视频";
                                        break;
                                    case TYPE_IMAGE:
                                        title = "相册";
                                        break;
                                }
                                allImageFolder.setFirstImagePath(allImages.get(0).getPath());
                                allImageFolder.setName(title);
                                allImageFolder.setType(type);
                                allImageFolder.setImages(allImages);
                                imageFolders.add(allImageFolder);
                                sortFolder(imageFolders);
                            }

                            if (imageFolders.size() > 0) {

                                adapter.bindFolderData(imageFolders);
//                                notifyDataCheckedStatus(selectMedias);
                            }
                            data.close();
                        } else {

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onLoaderReset(@NonNull Loader<Cursor> loader) {

            }
        });
    }


    private LocalMediaFolder getImageFolder(String path, List<LocalMediaFolder> imageFolders) {
        File imageFile = new File(path);
        File folderFile = imageFile.getParentFile();

        for (LocalMediaFolder folder : imageFolders) {
            if (folder.getName().equals(folderFile.getName())) {
                return folder;
            }
        }
        LocalMediaFolder newFolder = new LocalMediaFolder();
        newFolder.setName(folderFile.getName());
        newFolder.setPath(folderFile.getAbsolutePath());
        newFolder.setFirstImagePath(path);
        imageFolders.add(newFolder);
        return newFolder;
    }

    private void sortFolder(List<LocalMediaFolder> imageFolders) {
        // 文件夹按图片数量排序
        Collections.sort(imageFolders, new Comparator<LocalMediaFolder>() {
            @Override
            public int compare(LocalMediaFolder lhs, LocalMediaFolder rhs) {
                if (lhs.getImages() == null || rhs.getImages() == null) {
                    return 0;
                }
                int lsize = lhs.getImageNum();
                int rsize = rhs.getImageNum();
                return lsize == rsize ? 0 : (lsize < rsize ? 1 : -1);
            }
        });
    }
}
