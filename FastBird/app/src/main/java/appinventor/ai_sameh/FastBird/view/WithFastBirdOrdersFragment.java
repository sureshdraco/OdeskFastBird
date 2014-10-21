package appinventor.ai_sameh.FastBird.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import appinventor.ai_sameh.FastBird.R;
import appinventor.ai_sameh.FastBird.adapter.CardArrayAdapter;
import appinventor.ai_sameh.FastBird.model.Card;


public class WithFastBirdOrdersFragment extends Fragment {
 
 
	private TextView text;
    private ListView ordersListView;
    private CardArrayAdapter cardArrayAdapter;


    public WithFastBirdOrdersFragment() {
	}
 
 
	@Override 
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        ordersListView = (ListView) getActivity().findViewById(R.id.card_listView);

        cardArrayAdapter = new CardArrayAdapter(getActivity(), R.layout.orders_card_item);

        for (int i = 0; i < 10; i++) {
            Card card = new Card("Card " + (i+1) + " Line 1", "Card " + (i+1) + " Line 2");
            cardArrayAdapter.add(card);
        }
        ordersListView.setAdapter(cardArrayAdapter);
    }

    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return LayoutInflater.from(getActivity()).inflate(R.layout.orders_list_fragment,
                null);
    }
 
}