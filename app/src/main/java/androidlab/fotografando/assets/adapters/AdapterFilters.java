package androidlab.fotografando.assets.camera;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import androidlab.DB.Objects.Utente;
import androidlab.fotografando.R;
import androidlab.fotografando.assets.AsyncResponse;
import androidlab.fotografando.assets.listeners.UserOnClickListener;
import cn.Ragnarok.BitmapFilter;
import cn.Ragnarok.GrayFilter;

/**
 * Created by miki4 on 18/09/2017.
 */

public class AdapterFilters extends RecyclerView.Adapter<AdapterFilters.ViewHolder> {
    private List<Filter> items;
    private Context mContext;
    private Activity activity;
    private Intent intent;
    private ImageView canvas;
    private Bitmap bitmap;
    private Bitmap filterBitmap;

    public AdapterFilters( Context mContext,List<Filter> items, ImageView canvas,Intent intent) {
        this.items = items;
        this.mContext = mContext;
        this.intent = intent;
        this.canvas = canvas;
    }
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void setFilterBitmap(Bitmap filterBitmap) {
        this.filterBitmap = filterBitmap;
    }

    public Context getContext(){
        return mContext;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public CardView cardView;
        public ConstraintLayout layout;
        public TextView textViewName;
        public TextView textViewCost;

        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardView_item_filters_root);
            layout = (ConstraintLayout) itemView.findViewById(R.id.layout_item_filters_layout);
            textViewName = (TextView) itemView.findViewById(R.id.textView_item_filters_filterName);
            textViewCost = (TextView) itemView.findViewById(R.id.textView_item_filters_filterCost);
        }

    }
    @Override
    public AdapterFilters.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate a viewholder
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View viewFilters = inflater.inflate(R.layout.item_filters, parent, false);
        AdapterFilters.ViewHolder viewHolder = new AdapterFilters.ViewHolder(viewFilters);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AdapterFilters.ViewHolder holder, int position) {
        // Get the data model based on position
        final Filter filter = items.get(position);

        // Set item views based on your views and data model
        TextView textViewName= holder.textViewName;
        textViewName.setText(filter.toString());

        TextView textViewCost= holder.textViewCost;
        textViewCost.setText(Integer.toString(filter.getCost()));

        CardView cardView = holder.cardView;
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                canvas.setImageBitmap(null);
                canvas.setImageResource(0);
                canvas.setImageDrawable(null);
                switch (filter.toString()){
                    case "Grayscale":
                        filterBitmap = BitmapFilter.changeStyle(bitmap,BitmapFilter.GRAY_STYLE);
                        break;
                    case "Gotham":
                        filterBitmap = BitmapFilter.changeStyle(bitmap,BitmapFilter.GOTHAM_STYLE);
                        break;
                    case "Oil":
                        filterBitmap = BitmapFilter.changeStyle(bitmap,BitmapFilter.OIL_STYLE);
                        break;
                    case "Block":
                        filterBitmap = BitmapFilter.changeStyle(bitmap,BitmapFilter.BLOCK_STYLE);
                        break;
                    case "Blur":
                        filterBitmap = BitmapFilter.changeStyle(bitmap,BitmapFilter.AVERAGE_BLUR_STYLE);
                        break;
                    case "HDR":
                        filterBitmap = BitmapFilter.changeStyle(bitmap,BitmapFilter.HDR_STYLE);
                        break;
                    case "Light":
                        filterBitmap = BitmapFilter.changeStyle(bitmap,BitmapFilter.LIGHT_STYLE);
                        break;
                    case "Lomo":
                        filterBitmap = BitmapFilter.changeStyle(bitmap,BitmapFilter.LOMO_STYLE);
                        break;
                    case "Neon":
                        filterBitmap = BitmapFilter.changeStyle(bitmap,BitmapFilter.NEON_STYLE);
                        break;
                    case "Seppia":
                        filterBitmap = BitmapFilter.changeStyle(bitmap,BitmapFilter.OLD_STYLE);
                        break;
                    case "Pixel":
                        filterBitmap = BitmapFilter.changeStyle(bitmap,BitmapFilter.PIXELATE_STYLE);
                        break;
                    case "Relief":
                        filterBitmap = BitmapFilter.changeStyle(bitmap,BitmapFilter.RELIEF_STYLE);
                        break;
                    case "Sharpen":
                        filterBitmap = BitmapFilter.changeStyle(bitmap,BitmapFilter.SHARPEN_STYLE);
                        break;
                    case "Sketch":
                        filterBitmap = BitmapFilter.changeStyle(bitmap,BitmapFilter.SKETCH_STYLE);
                        break;
                    case "Glow":
                        filterBitmap = BitmapFilter.changeStyle(bitmap,BitmapFilter.SOFT_GLOW_STYLE);
                        break;
                    default:
                        filterBitmap = Bitmap.createBitmap(bitmap);
                }
                intent.putExtra("price",Integer.toString(filter.getCost()));
                canvas.setImageBitmap(filterBitmap);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
