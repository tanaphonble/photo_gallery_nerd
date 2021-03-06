package ayp.aug.photogallerynerd;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Tanaphon on 8/20/2016.
 */
public abstract class SingleFragmentActivity extends AppCompatActivity{
        @LayoutRes
            protected int getLayoutResId() {
                return
                        R.layout.activity_fragment;
            }

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(getLayoutResId());

                FragmentManager fm = getSupportFragmentManager();

                Fragment f = fm.findFragmentById(R.id.fragment_container); // first time null

                if (f == null) {
                    f = onCreateFragment();
                    fm.beginTransaction()
                            .add(R.id.fragment_container, f)
                            .commit();
                } else {
                }
            }
            protected abstract Fragment onCreateFragment();

}
