package org.biwi.rest;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.biwi.rest.model.AuctionsActive;
import org.biwi.rest.model.Bid;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


@ApplicationScoped
public class AuctionsActiveRepository implements PanacheRepository<AuctionsActive> {


    @Transactional
    public AuctionsActive findById(String id){

        return find("id", id).firstResult();
    }

    public List<AuctionsActive> getAll(){
        return listAll();
    }

    public AuctionsActive validateBid(String id, double value){

        AuctionsActive aa=this.findById(id);
        if(aa!=null){
            if (Double.compare(aa.getLastBidValue(), value)  >= 0 ){
                return null;
            }
            return aa;
        }
        return null;
    }


    public boolean addBid(AuctionsActive aa, String id, Bid bid){
        boolean added= aa.addBid(bid);
        if(added){
            return true;
        }
        return false;  
    }

    @Transactional
    public AuctionsActive newAuction(String id, LocalTime duration, double startingPrice,double reservePrice,String sellerID){
        AuctionsActive aa = new AuctionsActive(id,duration,startingPrice,reservePrice,sellerID);
        if(this.findById(id) ==null){
            persist(aa);
        }
        return aa;
    }


    @Transactional
    public AuctionsActive removeAuction(String id){
        AuctionsActive aa = this.findById(id);
        if(aa!= null){
            delete("id",id);
            return aa;
        }
        return null;
    }


    public List<AuctionsActive> getHotpicks (){
        LocalDateTime limit = LocalDateTime.now().plusHours(1).minusMinutes(10);
        PanacheQuery<AuctionsActive> query = find("select a from AuctionsActive a inner join a.bids as b where b.timeStamp > ?1 group by a.id order by sum(b.value) desc ", limit);
        return query.list();
    }

    @Transactional
    public boolean setOpen(String id, boolean status) {
        AuctionsActive auc = findById(id);
        auc.setOpen(status);
        return status;
    }



}