package androidlab.fotografando.assets;

import androidlab.fotografando.assets.ratings.RatingPhotosAdapter;
import androidlab.fotografando.assets.sessionList.ChallengeSessionAdapter;

/**
 * Created by miki4 on 29/05/2017.
 */

public interface AsyncResponse {
    void processSessionsFinish(ChallengeSessionAdapter output);
    void processRatingFinish(RatingPhotosAdapter output);
}
