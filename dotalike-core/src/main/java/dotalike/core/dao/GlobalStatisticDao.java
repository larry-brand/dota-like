package dotalike.core.dao;

import java.util.List;

import dotalike.common.model.external.PlayerGlobalDetails;

/**
 * Created by anstepan on 18.12.2015.
 */
public interface GlobalStatisticDao {
	
    public List<PlayerGlobalDetails> getPlayersDetails(List<Integer> id);
    
}