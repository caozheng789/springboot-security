import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import per.cz.security.SecurityApplication;
import per.cz.security.entity.RankDO;
import per.cz.security.service.impl.RankListComponent;

import java.util.List;


/**
 * Created by Administrator on 2020/5/24.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {SecurityApplication.class})
public class test {

	@Autowired
	private RankListComponent rankListComponent;

	@Test
	public void test() {


		RankDO rank = rankListComponent.getRank(11L);
		if (rank.getRank() == -1){
			rankListComponent.updateRank(11L, 30L);
		}else{
			rankListComponent.updateRank(11L, 49L+10);
		}


		List<RankDO> topNRanks = rankListComponent.getTopNRanks(10);
		for (RankDO r: topNRanks) {
			System.out.println(r.toString());
		}
	}
}
