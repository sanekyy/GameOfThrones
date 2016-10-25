package com.example.ihb.aleksandryurkovskiy.ui.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ihb.aleksandryurkovskiy.R;
import com.example.ihb.aleksandryurkovskiy.data.storage.models.Character;
import com.example.ihb.aleksandryurkovskiy.data.storage.models.Title;
import com.example.ihb.aleksandryurkovskiy.utils.ConstantManager;
import com.example.ihb.aleksandryurkovskiy.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;

/**
 * Created by ihb on 14.07.16.
 */
public class CharactersAdapter extends RecyclerView.Adapter<CharactersAdapter.CharacterViewHolder> {
    private static final String TAG = ConstantManager.TAG_PREFIX + " CharactersAdapter";

    Context mContext;
    List<Character> mCharacters;
    CharacterViewHolder.CustomClickListener mCustomClickListener;

    public CharactersAdapter(List<Character> characters, CharacterViewHolder.CustomClickListener customClickListener) {
        mCharacters = characters;
        this.mCustomClickListener = customClickListener;
    }

    @Override
    public CharactersAdapter.CharacterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_character_list,parent,false);
        return new CharacterViewHolder(convertView, mCustomClickListener);
    }

    @Override
    public void onBindViewHolder(final CharactersAdapter.CharacterViewHolder holder, int position) {
        final Character character = mCharacters.get(position);

        switch (character.getHome()){
            case ConstantManager.LANNISTERS_HOME: Picasso.with(mContext)
                    .load(R.drawable.lanister_icon)
                    .into(holder.mImage);
                break;
            case ConstantManager.STARKS_HOME: Picasso.with(mContext)
                    .load(R.drawable.stark_icon)
                    .into(holder.mImage);
                break;
            case ConstantManager.TARGARYENS_HOME: Picasso.with(mContext)
                    .load(R.drawable.targaryens_icon)
                    .into(holder.mImage);
                break;
        }

        holder.mName.setText(character.getName());
        if(character.getTitles()!=null&&character.getTitles().size()!=0) {
            List<String> titles = new ArrayList<>();
            for (Title title : character.getTitles()){
                titles.add(title.getTitle());
            }
            holder.mTitles.setText(Utils.joinString(titles, "\n"));
        } else {
            holder.mTitles.setText("Wanderer");
        }
    }

    @Override
    public int getItemCount() {
        return mCharacters.size();
    }

    public List<Character> getCharacters() {
        return mCharacters;
    }

    public static class CharacterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        protected ImageView mImage;
        protected TextView mName, mTitles;

        private  CustomClickListener mListener;

        public CharacterViewHolder(View itemView, CustomClickListener clickListener) {
            super(itemView);
            this.mListener = clickListener;

            mImage = (ImageView) itemView.findViewById(R.id.image_iv);
            mName = (TextView) itemView.findViewById(R.id.name_tv);
            mTitles = (TextView) itemView.findViewById(R.id.titles_tv);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if(mListener != null){
                mListener.onCharacterItemClickListener(getAdapterPosition());
            }
        }


        public interface CustomClickListener{

            void onCharacterItemClickListener(int position);
        }
    }
}
