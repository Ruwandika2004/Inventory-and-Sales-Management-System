package repository;

import com.supermarket.inventory.entity.StockTransaction;
import java.util.List;
import java.util.Optional;

public interface StockTransactionRepository {
    void save(StockTransaction transaction);
    List<StockTransaction> findAll();
    Optional<StockTransaction> findById(String id);
    void deleteById(String id);
    List<StockTransaction> findByProductId(String productId);
}
