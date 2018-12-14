package hoanglong.thesis.graduation.juncomputer.screen.phone.detail_product;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import hoanglong.thesis.graduation.juncomputer.R;
import hoanglong.thesis.graduation.juncomputer.data.model.comment.Comment;
import hoanglong.thesis.graduation.juncomputer.data.model.phone_product.ItemPhoneProduct;
import hoanglong.thesis.graduation.juncomputer.data.model.user.User;
import hoanglong.thesis.graduation.juncomputer.screen.base.BaseFragment;
import hoanglong.thesis.graduation.juncomputer.service.BaseService;
import hoanglong.thesis.graduation.juncomputer.utils.Constant;
import hoanglong.thesis.graduation.juncomputer.utils.FormatDate;
import hoanglong.thesis.graduation.juncomputer.utils.SharedPrefs;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class CommentFragment extends BaseFragment implements View.OnClickListener {
    public static final String TAG = CommentFragment.class.getName();
    public static final String BUNDLE_PHONE = "BUNDLE_PHONE";
    private static final int IMG_REQUEST = 1;
    private static final int TAKE_PHOTO_CODE = 2;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 3;
    private static final String DOMAIN = "MayBE";

    @BindView(R.id.image_phone)
    ImageView mImagePhone;
    @BindView(R.id.text_phone)
    TextView mTextPhone;
    @BindView(R.id.ic_back)
    ImageView mImageBack;
    private Listener mListener;
    @BindView(R.id.rating_comment)
    RatingBar mRatingBar;
    @BindView(R.id.relative_choose_image)
    RelativeLayout mRelativeChooseImage;
    @BindView(R.id.et_title)
    EditText mEditTitleComment;
    @BindView(R.id.et_comment)
    EditText mEditTextComment;
    @BindView(R.id.image_choose)
    ImageView mImageChoose;
    @BindView(R.id.image_comment)
    ImageView mImageComment;
    @BindView(R.id.relative_send_comment)
    RelativeLayout mRelativeSendComment;
    private Uri mPath;
    boolean mIsGallery = true;
    private Bitmap mBitmap;
    private String mNameOfImage;
    private ProgressDialog mProgressDialog;
    private ItemPhoneProduct phoneProduct;
    private User mUser;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (Listener) context;
    }

    public static CommentFragment newInstance(ItemPhoneProduct itemPhoneProduct) {
        Bundle args = new Bundle();
        args.putParcelable(BUNDLE_PHONE, itemPhoneProduct);
        CommentFragment fragment = new CommentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResources() {
        return R.layout.fragment_comment;
    }

    @Override
    protected void initComponent(View view) {
        ButterKnife.bind(this, view);
        mImageBack.setOnClickListener(this);
        mRelativeChooseImage.setOnClickListener(this);
        mRelativeSendComment.setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        Bundle bundle = this.getArguments();
        if (bundle == null) {
            return;
        }
        Gson gson = new Gson();
        String json = SharedPrefs.getInstance().get(Constant.Login.OBJECT_USER_LOGIN, String.class);
        mUser = gson.fromJson(json, User.class);
        if (mUser == null) {
            return;
        }
        phoneProduct = bundle.getParcelable(BUNDLE_PHONE);
        if (phoneProduct == null) {
            return;
        }
        mTextPhone.setText(phoneProduct.getTitle());
        if (getContext() == null) {
            return;
        }
        Glide.with(getContext()).load(phoneProduct.getImage()).into(mImagePhone);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ic_back:
                if (getFragmentManager() != null) {
                    getFragmentManager().popBackStack();
                }
                break;
            case R.id.relative_choose_image:
                chooseImage();
                break;
            case R.id.relative_send_comment:
                checkValid();
                break;
        }
    }

    private void checkValid() {
        showProgress("Upload comment");
        if (getContext() == null) return;
        if (mEditTextComment.getText().toString().isEmpty()) {
            Toasty.warning(getContext(), "Bạn không được bỏ trống bình luận", Toast.LENGTH_SHORT, true).show();
        } else {
            sendComment();
        }
    }

    private void sendComment() {
        String date = FormatDate.getCurrentDate();
        String idProduct = phoneProduct.getId();
        String nameProduct = phoneProduct.getTitle();
        String nameUser = mUser.getFullName();
        String image = null;
        if (mNameOfImage != null) {
            image = "https://res.cloudinary.com/hoanglongb/image/upload/v1544258437/" + mNameOfImage + ".jpg";
        }
        String titleComment = mEditTitleComment.getText().toString();
        String comment = mEditTextComment.getText().toString();
        float rating = mRatingBar.getRating();

        Comment commentUpload = new Comment(date, idProduct, nameProduct, nameUser, image, titleComment, comment, rating);
        Call<Comment> call = BaseService.getService().createComment(commentUpload);
        call.enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(@NonNull Call<Comment> call, @NonNull Response<Comment> response) {
                if (response.body() != null) {
                    hideProgress();
                    if (getFragmentManager() != null) {
                        getFragmentManager().popBackStack();
                        mListener.onUpdateComment();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Comment> call, @NonNull Throwable t) {

            }
        });
    }

    private void chooseImage() {
        if (getContext() == null) return;
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Choose Image Source");
        builder.setItems(new CharSequence[]{"Gallery", "Camera"},
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(intent, IMG_REQUEST);
                                break;
                            case 1:
                                showCameraPreview();
                                break;
                        }
                    }
                });

        builder.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_CAMERA) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera();
            } else {
                Toast.makeText(getContext(), "camera_permission_denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void startCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, TAKE_PHOTO_CODE);
    }

    private void showCameraPreview() {
        if (getContext() == null) return;
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            // Permission is already available, start camera preview
            startCamera();
        } else {
            // Permission is missing and must be requested.
            requestCameraPermission();
        }
    }

    private void requestCameraPermission() {
        if (getActivity() == null) {
            return;
        }
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                Manifest.permission.CAMERA)) {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CAMERA},
                    MY_PERMISSIONS_REQUEST_CAMERA);

        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        showProgress("Uploading...");
        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null) {
            if (getActivity() == null) return;
            mPath = data.getData();
            try {
                mBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), mPath);
                new UploadImage().execute();
                mImageComment.setImageBitmap(mBitmap);
                mImageChoose.setVisibility(View.GONE);

            } catch (IOException e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            mIsGallery = true;
        }
        if (requestCode == TAKE_PHOTO_CODE && resultCode == RESULT_OK && data != null) {
            if (data.getExtras() == null) {
                return;
            }
            mBitmap = (Bitmap) data.getExtras().get("data");
            mImageComment.setImageBitmap(mBitmap);
            mImageChoose.setVisibility(View.GONE);
            mIsGallery = false;
        }
    }

    private void uploadImage() {
        if (getActivity() == null) return;
        Map config = new HashMap();
        config.put("cloud_name", Constant.Cloudinary.CLOUD_NAME);
        config.put("api_key", Constant.Cloudinary.API_KEY);
        config.put("api_secret", Constant.Cloudinary.SECRET_KEY);
        Cloudinary cloudinary = new Cloudinary(Constant.Cloudinary.URL);
        //TODO: id fb of user
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        Date currentTime = Calendar.getInstance().getTime();
        String time = calendar.get(Calendar.YEAR) + "" + calendar.get(Calendar.MONTH) + "" + calendar.get(Calendar.DATE)
                + "" + currentTime.getHours() + "" + currentTime.getMinutes() + "" + currentTime.getSeconds();
        mNameOfImage = DOMAIN + time;
        cloudinary.url()
                .transformation(new Transformation().width(300).crop("fill"))
                .generate(mNameOfImage + ".jpg");
        try {

            InputStream inputStream;
            if (mIsGallery) {
                inputStream = getActivity().getContentResolver().openInputStream(mPath);
                cloudinary.uploader().upload(inputStream, ObjectUtils.asMap("public_id", mNameOfImage));
            } else {
                String myBase64Image = encodeToBase64(mBitmap);
                cloudinary.uploader().upload(myBase64Image, ObjectUtils.asMap("public_id", mNameOfImage));

            }
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    hideProgress();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String encodeToBase64(Bitmap image) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return "data:image/png;base64," + Base64.encodeToString(byteArray, Base64.DEFAULT);
    }


    class UploadImage extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            uploadImage();
            return null;
        }
    }

    public void showProgress(String title) {
        if (mProgressDialog != null) {
            return;
        }
        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setMessage(title);
        mProgressDialog.show();
    }

    public void hideProgress() {
        if (mProgressDialog == null)
            return;
        mProgressDialog.dismiss();
        mProgressDialog = null;
    }

}
