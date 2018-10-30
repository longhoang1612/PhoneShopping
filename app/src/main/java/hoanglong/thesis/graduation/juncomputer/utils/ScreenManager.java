package hoanglong.thesis.graduation.juncomputer.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by apple on 3/28/18.
 */

public class ScreenManager {
    public static void openFragment(FragmentManager fragmentManager,
                                    Fragment fragment, int layoutID) {
        if(!fragment.isAdded()){
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(layoutID, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }else{
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.show(fragment);
        }
    }
}
