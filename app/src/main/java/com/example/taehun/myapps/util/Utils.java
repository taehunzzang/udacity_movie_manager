package com.example.taehun.myapps.util;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by taehun on 15. 6. 30..
 */
public class Utils {
    private String TAG = Utils.class.getSimpleName();
    private Context _context;
    public Utils(Context context) {
        this._context = context;
    }

    public int getScreenWidth() {
        int columnWidth;
        WindowManager wm = (WindowManager) _context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        final Point point = new Point();
        try {
            display.getSize(point);
        } catch (java.lang.NoSuchMethodError ignore) {
            // Older device
            point.x = display.getWidth();
            point.y = display.getHeight();
        }
        columnWidth = point.x;
        return columnWidth;
    }
    static void initScreen(){
        DisplayMetrics metrics = new DisplayMetrics();

        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
    }


//    public void saveImageToSDCard(Bitmap bitmap) {
//        File myDir = new File(
//                Environment
//                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
//                pref.getGalleryName());
//
//        myDir.mkdirs();
//        Random generator = new Random();
//        int n = 10000;
//        n = generator.nextInt(n);
//        String fname = "Wallpaper-" + n + ".jpg";
//        File file = new File(myDir, fname);
//        if (file.exists())
//            file.delete();
//        try {
//            FileOutputStream out = new FileOutputStream(file);
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
//            out.flush();
//            out.close();
//            Toast.makeText(
//                    _context,
//                    _context.getString(R.string.toast_saved).replace("#",
//                            "\"" + pref.getGalleryName() + "\""),
//                    Toast.LENGTH_SHORT).show();
//            Log.d(TAG, "Wallpaper saved to: " + file.getAbsolutePath());
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public void setAsWallpaper(Bitmap bitmap) {
        try {
            WallpaperManager wm = WallpaperManager.getInstance(_context);

            wm.setBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
