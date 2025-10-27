package com.examly.springapp.configuration;

import com.examly.springapp.model.*;
import com.examly.springapp.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class DataInitializer implements CommandLineRunner {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final MenuRepository menuRepository;
    private final MenuItemRepository menuItemRepository;

    public DataInitializer(ItemRepository itemRepository, UserRepository userRepository,
                          MenuRepository menuRepository, MenuItemRepository menuItemRepository) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
        this.menuRepository = menuRepository;
        this.menuItemRepository = menuItemRepository;
    }

    @Override
    public void run(String... args) {
        try {
            createDefaultUsers();
            createDefaultItems();
            createSampleMenusAndItems();
        } catch (Exception e) {
            System.err.println("Error initializing data: " + e.getMessage());
        }
    }
    
    private void createDefaultItems() {
        if (itemRepository.count() == 0) {
            // Continental
            createItem("Pasta Carbonara", "Continental", 450f, "Creamy pasta with bacon", "https://images.unsplash.com/photo-1621996346565-e3dbc353d2e5?w=400");
            createItem("Grilled Chicken", "Continental", 550f, "Herb-seasoned grilled chicken", "https://images.unsplash.com/photo-1532550907401-a500c9a57435?w=400");
            createItem("Caesar Salad", "Continental", 320f, "Fresh romaine with caesar dressing", "https://images.unsplash.com/photo-1546793665-c74683f339c1?w=400");

            // Indian
            createItem("Butter Chicken", "Indian", 380f, "Creamy tomato-based curry", "https://images.unsplash.com/photo-1565557623262-b51c2513a641?w=400");
            createItem("Biryani", "Indian", 280f, "Fragrant basmati rice with spices", "https://images.unsplash.com/photo-1563379091339-03246963d51a?w=400");
            createItem("Naan Bread", "Indian", 45f, "Traditional Indian flatbread", "https://images.unsplash.com/photo-1601050690597-df0568f70950?w=400");

            // Arabic
            createItem("Shawarma", "Arabic", 250f, "Marinated meat in pita bread", "https://images.unsplash.com/photo-1529006557810-274b9b2fc783?w=400");
            createItem("Hummus", "Arabic", 180f, "Chickpea dip with tahini", "https://images.unsplash.com/photo-1571197119282-7c4d9e5e6e4e?w=400");
            createItem("Falafel", "Arabic", 220f, "Deep-fried chickpea balls", "https://images.unsplash.com/photo-1593504049359-74330189a345?w=400");

            // Chinese
            createItem("Sweet & Sour Pork", "Chinese", 420f, "Crispy pork in tangy sauce", "https://images.unsplash.com/photo-1559847844-d721426d6edc?w=400");
            createItem("Fried Rice", "Chinese", 200f, "Wok-fried rice with vegetables", "https://images.unsplash.com/photo-1512058564366-18510be2db19?w=400");
            createItem("Spring Rolls", "Chinese", 150f, "Crispy vegetable rolls", "https://images.unsplash.com/photo-1544982503-9f984c14501a?w=400");

            // South Indian
            createItem("Dosa", "South Indian", 120f, "Crispy rice and lentil crepe", "https://images.unsplash.com/photo-1567188040759-fb8a883dc6d8?w=400");
            createItem("Idli", "South Indian", 80f, "Steamed rice cakes", "https://images.unsplash.com/photo-1630383249896-424e482df921?w=400");
            createItem("Sambar", "South Indian", 60f, "Lentil curry with vegetables", "https://images.unsplash.com/photo-1596797038530-2c107229654b?w=400");
            
            System.out.println("Created " + itemRepository.count() + " food items");
        } else {
            System.out.println("Items already exist: " + itemRepository.count());
        }
    }

    private void createItem(String name, String category, Float price, String description, String imageUrl) {
        Item item = new Item();
        item.setItemName(name);
        item.setCategory(category);
        item.setPrice(price);
        item.setAvailable(true);
        item.setCalories(300);
        item.setDescription(description);
        item.setImageUrl(imageUrl);
        item.setCreatedAt(java.time.LocalDateTime.now());
        item.setUpdatedAt(java.time.LocalDateTime.now());
        item.setCreatedBy("system");
        item.setUpdatedBy("system");
        itemRepository.save(item);
    }

    private void createDefaultUsers() {
        System.out.println("User count: " + userRepository.count());
        if (userRepository.count() <= 2) {
            // Owner account
            createUserIfNotExists("owner", "owner123", "OWNER");
            
            // Multiple customer accounts
            createUserIfNotExists("john", "john123", "CUSTOMER");
            createUserIfNotExists("sarah", "sarah123", "CUSTOMER");
            createUserIfNotExists("mike", "mike123", "CUSTOMER");
            createUserIfNotExists("priya", "priya123", "CUSTOMER");
            createUserIfNotExists("alex", "alex123", "CUSTOMER");
            createUserIfNotExists("customer", "customer123", "CUSTOMER");
            
            System.out.println("Created " + userRepository.count() + " users");
        } else {
            System.out.println("Users already exist: " + userRepository.count());
        }
    }
    
    private void createUserIfNotExists(String username, String password, String role) {
        if (userRepository.findByUsername(username).isEmpty()) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setRole(role);
            userRepository.save(user);
            System.out.println("Created user: " + username + " (" + role + ")");
        }
    }
    
    private void createSampleMenusAndItems() {
        if (menuRepository.count() == 0) {
            // Create diverse menus
            Menu indianMenu = menuRepository.save(new Menu("Spice Garden", 1L, "Authentic Indian cuisine"));
            Menu americanMenu = menuRepository.save(new Menu("Stars & Stripes Diner", 1L, "Classic American food"));
            Menu chineseMenu = menuRepository.save(new Menu("Golden Dragon", 2L, "Traditional Chinese dishes"));
            Menu japaneseMenu = menuRepository.save(new Menu("Sakura Sushi", 2L, "Fresh Japanese cuisine"));
            Menu europeanMenu = menuRepository.save(new Menu("Europa Bistro", 3L, "European delicacies"));
            
            // Indian menu items (₹)
            createMenuItem(indianMenu, "Butter Chicken", "Creamy tomato-based curry with tender chicken", "450", "Indian", "chicken,curry,creamy,popular", "https://images.unsplash.com/photo-1565557623262-b51c2513a641?w=400");
            createMenuItem(indianMenu, "Biryani", "Fragrant basmati rice with spiced meat", "380", "Indian", "rice,spicy,aromatic,traditional", "https://images.unsplash.com/photo-1563379091339-03246963d51a?w=400");
            createMenuItem(indianMenu, "Masala Dosa", "Crispy rice crepe with spiced potato filling", "180", "Indian", "vegetarian,south-indian,crispy", "https://images.unsplash.com/photo-1567188040759-fb8a883dc6d8?w=400");
            createMenuItem(indianMenu, "Paneer Tikka", "Grilled cottage cheese with spices", "320", "Indian", "vegetarian,grilled,spicy", "https://images.unsplash.com/photo-1599487488170-d11ec9c172f0?w=400");
            
            // American menu items
            createMenuItem(americanMenu, "Classic Burger", "Beef patty with lettuce, tomato, and cheese", "520", "American", "beef,cheese,classic", "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=400");
            createMenuItem(americanMenu, "BBQ Ribs", "Slow-cooked pork ribs with BBQ sauce", "680", "American", "pork,bbq,smoky", "https://images.unsplash.com/photo-1544025162-d76694265947?w=400");
            createMenuItem(americanMenu, "Mac and Cheese", "Creamy macaroni with three cheeses", "280", "American", "pasta,cheese,comfort-food", "https://images.unsplash.com/photo-1543826173-1ad8b2b8b6b8?w=400");
            createMenuItem(americanMenu, "Buffalo Wings", "Spicy chicken wings with blue cheese dip", "420", "American", "chicken,spicy,wings", "https://images.unsplash.com/photo-1527477396000-e27163b481c2?w=400");
            
            // Chinese menu items
            createMenuItem(chineseMenu, "Sweet & Sour Pork", "Crispy pork with tangy sauce", "380", "Chinese", "pork,sweet,tangy", "https://images.unsplash.com/photo-1559847844-d721426d6edc?w=400");
            createMenuItem(chineseMenu, "Kung Pao Chicken", "Spicy chicken with peanuts and vegetables", "350", "Chinese", "chicken,spicy,peanuts", "https://images.unsplash.com/photo-1596797038530-2c107229654b?w=400");
            createMenuItem(chineseMenu, "Fried Rice", "Wok-fried rice with vegetables and egg", "220", "Chinese", "rice,vegetables,wok-fried", "https://images.unsplash.com/photo-1512058564366-18510be2db19?w=400");
            createMenuItem(chineseMenu, "Dim Sum Platter", "Assorted steamed dumplings", "480", "Chinese", "dumplings,steamed,variety", "https://images.unsplash.com/photo-1496116218417-1a781b1c416c?w=400");
            
            // Japanese menu items
            createMenuItem(japaneseMenu, "Sushi Combo", "Fresh sushi with salmon, tuna, and shrimp", "650", "Japanese", "sushi,fresh,seafood", "https://images.unsplash.com/photo-1579584425555-c3ce17fd4351?w=400");
            createMenuItem(japaneseMenu, "Chicken Teriyaki", "Grilled chicken with teriyaki glaze", "420", "Japanese", "chicken,grilled,teriyaki", "https://images.unsplash.com/photo-1546833999-b9f581a1996d?w=400");
            createMenuItem(japaneseMenu, "Ramen Bowl", "Rich pork broth with noodles and toppings", "380", "Japanese", "noodles,broth,comfort-food", "https://images.unsplash.com/photo-1569718212165-3a8278d5f624?w=400");
            createMenuItem(japaneseMenu, "Tempura Platter", "Lightly battered and fried vegetables and shrimp", "450", "Japanese", "tempura,fried,vegetables,shrimp", "https://images.unsplash.com/photo-1541781774459-bb2af2f05b55?w=400");
            
            // European menu items
            createMenuItem(europeanMenu, "Margherita Pizza", "Classic Italian pizza with tomato and mozzarella", "420", "European", "pizza,italian,vegetarian", "https://images.unsplash.com/photo-1621996346565-e3dbc353d2e5?w=400");
            createMenuItem(europeanMenu, "Fish and Chips", "British-style battered fish with fries", "480", "European", "fish,british,fried", "https://images.unsplash.com/photo-1544982503-9f984c14501a?w=400");
            createMenuItem(europeanMenu, "Beef Stroganoff", "Russian beef in creamy mushroom sauce", "520", "European", "beef,creamy,mushroom,russian", "https://images.unsplash.com/photo-1572441713132-51c75654db73?w=400");
            createMenuItem(europeanMenu, "Paella", "Spanish rice dish with seafood and saffron", "580", "European", "rice,seafood,spanish,saffron", "https://images.unsplash.com/photo-1534080564583-6be75777b70a?w=400");
            
            System.out.println("Created sample menus and items: " + menuItemRepository.count() + " items");
        }
    }
    
    private void createMenuItem(Menu menu, String name, String description, String price, String category, String tags, String imageUrl) {
        MenuItem item = new MenuItem(menu, name, description, new BigDecimal(price), category, tags);
        item.setImageUrl(imageUrl);
        menuItemRepository.save(item);
    }
}