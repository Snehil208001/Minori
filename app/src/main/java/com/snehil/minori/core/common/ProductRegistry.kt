package com.snehil.minori.core.common

import com.snehil.minori.R
import com.snehil.minori.domain.model.WishlistItem

object ProductRegistry {
    // 1. Home Products (type = "Product")
    private val homeProducts = listOf(
        WishlistItem("p1", "Earthy Ceramic Bowl", "Hand-thrown clay bowl with natural white glaze.", 1850.0, 2500.0, "26% OFF", 4.7f, "ceramic_bowl", R.drawable.ceramic_bowl, "Ceramics", null, "Product"),
        WishlistItem("p2", "Terracotta Clay Pitcher", "Traditional rustic pitcher for fresh water or decor.", 2400.0, 3200.0, "25% OFF", 4.9f, "clay_pitcher", R.drawable.clay_pitcher, "Ceramics", null, "Product"),
        WishlistItem("p3", "Glass Vase / Carafe", "Blown decanter in warm amber gradient shade.", 2650.0, 5300.0, "50% OFF", 4.5f, "glass_carafe", R.drawable.glass_carafe, "Glassware", null, "Product"),
        WishlistItem("p4", "Oak Carved Chest", "Handcrafted organic frame drawer with brass keys.", 6850.0, 9500.0, "28% OFF", 4.8f, "oak_chest", R.drawable.oak_chest, "Woodwork", null, "Product"),
        WishlistItem("p5", "Rattan Lounge Chair", "Minimalist modern handwoven rattan armchair.", 8500.0, 12000.0, "29% OFF", 4.6f, "rattan_chair", R.drawable.rattan_chair, "Woodwork", null, "Product"),
        WishlistItem("p6", "Woven Tribal Rug", "Bohemian woven wool rug with geo-patterns.", 4900.0, 7000.0, "30% OFF", 4.7f, "wool_rug", R.drawable.wool_rug, "Fiber Art", null, "Product"),
        WishlistItem("p7", "Speckled Clay Mug", "Cozy speckled mug with flat base and large handle.", 850.0, 1200.0, "29% OFF", 4.8f, "ceramic_mug", R.drawable.ceramic_mug, "Ceramics", null, "Product"),
        WishlistItem("p8", "Scented Soy Candle", "Soy wax candle in a reusable clay pot container.", 950.0, 1500.0, "36% OFF", 4.9f, "boho_candle", R.drawable.boho_candle, "Paintings", null, "Product"),
        WishlistItem("p9", "Woven Wall Tapestry", "Handcrafted abstract cotton/wool wall hanging.", 3200.0, 4800.0, "33% OFF", 4.4f, "tapestry_wall", R.drawable.tapestry_wall, "Fiber Art", null, "Product"),
        WishlistItem("p10", "Fringe Seagrass Basket", "Large woven laundry or blanket hamper with detailed braids.", 1600.0, 2000.0, "20% OFF", 4.8f, "woven_basket", R.drawable.woven_basket, "Woodwork", null, "Product")
    )

    // 2. Trending Products (type = "TrendingProduct")
    private val trendingProducts = listOf(
        WishlistItem("1", "Earthy Ceramic Bowl", "Hand-thrown clay bowl with natural white glaze.", 1850.0, 2500.0, "26% OFF", 4.7f, null, R.drawable.ceramic_bowl, "Clayware", null, "TrendingProduct"),
        WishlistItem("2", "Terracotta Clay Pitcher", "Traditional rustic pitcher for fresh water or decor.", 2400.0, 3200.0, "25% OFF", 4.9f, null, R.drawable.clay_pitcher, "Clayware", null, "TrendingProduct"),
        WishlistItem("3", "Glass Vase / Carafe", "Blown decanter in warm amber gradient shade.", 2650.0, 5300.0, "50% OFF", 4.5f, null, R.drawable.glass_carafe, "Decor", null, "TrendingProduct"),
        WishlistItem("4", "Oak Carved Chest", "Handcrafted organic frame drawer with brass keys.", 6850.0, 9500.0, "28% OFF", 4.8f, null, R.drawable.oak_chest, "Furniture", null, "TrendingProduct"),
        WishlistItem("5", "Rattan Lounge Chair", "Minimalist modern handwoven rattan armchair.", 8500.0, 12000.0, "29% OFF", 4.6f, null, R.drawable.rattan_chair, "Furniture", null, "TrendingProduct"),
        WishlistItem("6", "Woven Tribal Rug", "Bohemian woven wool rug with geo-patterns.", 4900.0, 7000.0, "30% OFF", 4.7f, null, R.drawable.wool_rug, "Textiles", null, "TrendingProduct"),
        WishlistItem("7", "Speckled Clay Mug", "Cozy speckled mug with flat base and large handle.", 850.0, 1200.0, "29% OFF", 4.8f, null, R.drawable.ceramic_mug, "Clayware", null, "TrendingProduct"),
        WishlistItem("8", "Scented Soy Candle", "Soy wax candle in a reusable clay pot container.", 950.0, 1500.0, "36% OFF", 4.9f, null, R.drawable.boho_candle, "Decor", null, "TrendingProduct"),
        WishlistItem("9", "Woven Wall Tapestry", "Handcrafted abstract cotton/wool wall hanging.", 3200.0, 4800.0, "33% OFF", 4.4f, null, R.drawable.tapestry_wall, "Textiles", null, "TrendingProduct"),
        WishlistItem("10", "Pottery Workshop Class", "Hands-on pottery workshop with a master artisan.", 1500.0, 3000.0, "50% OFF", 5.0f, null, R.drawable.pottery_class, "Decor", null, "TrendingProduct")
    )

    // 3. Ceramic Products (type = "Ceramic")
    private val ceramicProducts = listOf(
        WishlistItem("1", "Speckled Stoneware Vase", "Elegant tall vase hand-thrown on the wheel, coated in oatmeal glaze.", 2800.0, 3500.0, "20% OFF", 4.8f, null, R.drawable.clay_pitcher, "Stoneware", "Firing Temp: 1220°C", "Ceramic"),
        WishlistItem("2", "Porcelain Sake Set", "Translucent white porcelain flask with four small cups, fine celadon wash.", 4200.0, 5000.0, "16% OFF", 4.9f, null, R.drawable.glass_carafe, "Porcelain", "Firing Temp: 1300°C", "Ceramic"),
        WishlistItem("3", "Matte Terra Serving Bowl", "Rustic wide serving bowl, raw terracotta exterior with glazed center.", 1900.0, 2500.0, "24% OFF", 4.7f, null, R.drawable.ceramic_bowl, "Earthenware", "Firing Temp: 1080°C", "Ceramic"),
        WishlistItem("4", "Raku Tea Cup", "Traditional crackle-glazed tea bowl, fired in a gas-fueled kiln.", 1500.0, 2000.0, "25% OFF", 5.0f, null, R.drawable.ceramic_mug, "Raku", "Firing Temp: 950°C", "Ceramic")
    )

    // 4. Painting Products (type = "Painting")
    private val paintingProducts = listOf(
        WishlistItem("1", "Coastal Mist Oil", "Impressionist coastal scene painted with thick palette knife strokes on stretched canvas.", 14500.0, 18000.0, "19% OFF", 4.9f, null, R.drawable.artisan_weaving, "Oil Painting", "24x36 in", "Painting"),
        WishlistItem("2", "Crimson Sunrise", "Vibrant wet-on-wet watercolor study capturing dawn over misty hills.", 3500.0, 4500.0, "22% OFF", 4.7f, null, R.drawable.tapestry_wall, "Watercolor Painting", "12x16 in", "Painting"),
        WishlistItem("3", "Abstract Calm", "Minimalist abstract canvas featuring gentle earth tones and textured plaster lines.", 9500.0, 12000.0, "20% OFF", 4.8f, null, R.drawable.glass_carafe, "Acrylic Painting", "30x30 in", "Painting"),
        WishlistItem("4", "Forest Path Gouache", "Intimate botanical Gouache painting on heavy cotton paper, detailing forest light.", 2200.0, 3000.0, "26% OFF", 4.6f, null, R.drawable.boho_candle, "Gouache Painting", "10x14 in", "Painting")
    )

    // 5. Fine Arts Products (type = "FineArts")
    private val fineArtsProducts = listOf(
        WishlistItem("1", "Bronze Abstract Form", "Heavy, lost-wax cast bronze sculpture on a black marble base.", 28000.0, 32000.0, "12% OFF", 4.9f, null, R.drawable.pottery_class, "Sculpture", "Cast Bronze | Edition: 1 of 10", "FineArts"),
        WishlistItem("2", "Monotype Linocut", "Original graphic linoleum block print using soy-based black inks on handmade mulberry paper.", 4800.0, 6000.0, "20% OFF", 4.8f, null, R.drawable.tapestry_wall, "Printmaking", "Mulberry Paper | Edition: 4 of 25", "FineArts"),
        WishlistItem("3", "Hand-Forged Brass Bowl", "Sculptural vessel hammered by hand, displaying rich fire scale patina.", 8900.0, 11000.0, "19% OFF", 4.7f, null, R.drawable.ceramic_bowl, "Metal Art", "Hammered Brass | Edition: Unique 1/1", "FineArts"),
        WishlistItem("4", "Carved Alabaster Vessel", "Translucent carved stone vessel with organic natural veins.", 16000.0, 20000.0, "20% OFF", 5.0f, null, R.drawable.glass_carafe, "Sculpture", "White Alabaster | Edition: Unique 1/1", "FineArts")
    )

    // 6. Artisan Products (type = "ArtisanProduct")
    private val artisanProducts = listOf(
        WishlistItem("1", "Heritage Loom Rug", "Hand-knotted wool area rug featuring intricate geometrical motifs.", 5200.0, 6500.0, "20% OFF", 4.9f, null, R.drawable.artisan_weaving, "Weaving", "Artisan: Ananya Sen", "ArtisanProduct"),
        WishlistItem("2", "Woven Cotton Tapestry", "Delicate textured macrame wall art with driftwood mount.", 2400.0, 3000.0, "20% OFF", 4.7f, null, R.drawable.tapestry_wall, "Tapestry", "Artisan: Kabir Dev", "ArtisanProduct"),
        WishlistItem("3", "Earthy Plant Hanger", "Triple tiered bohemian heavy rope cotton plant holder.", 800.0, 1000.0, "20% OFF", 4.8f, null, R.drawable.macrame_hanger, "Tapestry", "Artisan: Meera Nair", "ArtisanProduct"),
        WishlistItem("4", "Bohemian Wool Runner", "Narrow hallway runner flatweave with neutral tone tassels.", 3600.0, 4500.0, "20% OFF", 4.6f, null, R.drawable.wool_rug, "Weaving", "Artisan: Ananya Sen", "ArtisanProduct"),
        WishlistItem("5", "Fringe Seagrass Basket", "Large woven laundry or blanket hamper with detailed braids.", 1600.0, 2000.0, "20% OFF", 4.8f, null, R.drawable.woven_basket, "Baskets", "Artisan: Raju Prasad", "ArtisanProduct"),
        WishlistItem("6", "Tufted Linen Cushion", "Soft organic flax linen pillow with cotton tufted designs.", 1200.0, 1500.0, "20% OFF", 4.7f, null, R.drawable.linen_pillow, "Cushions", "Artisan: Kabir Dev", "ArtisanProduct")
    )

    // 7. Arrival Products (type = "ArrivalProduct")
    private val arrivalProducts = listOf(
        WishlistItem("1", "Organic Linen Tote", "Spacious summer tote bag with braided handles and hand-frayed fringe detailing.", 1450.0, 2000.0, "27% OFF", 4.8f, null, R.drawable.linen_tote, "Bags", "100% Organic Linen", "ArrivalProduct"),
        WishlistItem("2", "Rustic Linen Pillow", "Heavy-texture cream linen throw pillow cover with organic flax fibers.", 1200.0, 1600.0, "25% OFF", 4.6f, null, R.drawable.linen_pillow, "Home Decor", "100% Organic Linen", "ArrivalProduct"),
        WishlistItem("3", "Hand-Braided Jute Rug", "Earthy natural round braided jute fiber rug for entryways.", 3200.0, 4000.0, "20% OFF", 4.7f, null, R.drawable.wool_rug, "Home Decor", "Natural Jute Fiber", "ArrivalProduct"),
        WishlistItem("4", "Seagrass Market Basket", "Handwoven sturdy market carrier bag with brown leather handles.", 1800.0, 2400.0, "25% OFF", 4.8f, null, R.drawable.woven_basket, "Bags", "Seagrass & Leather", "ArrivalProduct"),
        WishlistItem("5", "Fringe Cotton Hanger", "Bohemian hand-knotted cotton rope hanger for plant pots.", 650.0, 900.0, "27% OFF", 4.5f, null, R.drawable.macrame_hanger, "Home Decor", "Organic Cotton Rope", "ArrivalProduct"),
        WishlistItem("6", "Abstract Canvas Hanging", "Minimalist abstract screen-printed linen wall banner tapestry.", 2200.0, 3000.0, "26% OFF", 4.7f, null, R.drawable.tapestry_wall, "Home Decor", "Handwoven Cotton Linen", "ArrivalProduct")
    )

    // 8. New Products (type = "NewProduct")
    private val newProducts = listOf(
        WishlistItem("1", "Hand-Carved Mirror", "Ornate round wall mirror with detailed floral woodwork carvings.", 4200.0, 6000.0, "30% OFF", 4.9f, null, R.drawable.carved_mirror, "Mirrors", null, "NewProduct"),
        WishlistItem("2", "Rustic Oak Chest", "Solid oak storage drawer with brass pull rings and key.", 7800.0, 11000.0, "29% OFF", 4.8f, null, R.drawable.oak_chest, "Furniture", null, "NewProduct"),
        WishlistItem("3", "Rattan Armchair", "Handwoven natural rattan lounge chair with soft cushions.", 9200.0, 13000.0, "29% OFF", 4.7f, null, R.drawable.rattan_chair, "Furniture", null, "NewProduct"),
        WishlistItem("4", "Woven Cotton Basket", "Minimal storage basket with sturdy rope carrying handles.", 1100.0, 1500.0, "26% OFF", 4.6f, null, R.drawable.woven_basket, "Accessories", null, "NewProduct"),
        WishlistItem("5", "Soy Wax Clay Candle", "Scented organic candle in a reusable handthrown clay container.", 850.0, 1200.0, "29% OFF", 4.9f, null, R.drawable.boho_candle, "Decor", null, "NewProduct"),
        WishlistItem("6", "Sage Pottery Carafe", "Rustic water pitcher with textured Sage color wash details.", 1800.0, 2400.0, "25% OFF", 4.8f, null, R.drawable.clay_pitcher, "Clayware", null, "NewProduct")
    )

    // 9. Pottery Products (type = "PotteryProduct")
    private val potteryProducts = listOf(
        WishlistItem("1", "Pottery Wheel Workshop", "A 2-hour guided session on the spinning potter's wheel. Clay and tools provided.", 1800.0, 2400.0, "25% OFF", 5.0f, null, R.drawable.pottery_spinning, "Classes", "Beginner Friendly", "PotteryProduct"),
        WishlistItem("2", "Master Pottery Course", "Weekend intensive course covering advanced glazing, trim work, and kiln firing.", 3200.0, 4000.0, "20% OFF", 4.9f, null, R.drawable.pottery_class, "Classes", "Advanced", "PotteryProduct"),
        WishlistItem("3", "Handthrown Speckled Bowl", "Earthy natural white glazed stoneware bowl for salads or display.", 950.0, 1200.0, "20% OFF", 4.8f, null, R.drawable.ceramic_bowl, "Clayware", "Stoneware", "PotteryProduct"),
        WishlistItem("4", "Earthy Terracotta Pitcher", "Traditional unglazed clay vessel for fresh water cooling.", 1300.0, 1800.0, "27% OFF", 4.7f, null, R.drawable.clay_pitcher, "Clayware", "Terracotta", "PotteryProduct"),
        WishlistItem("5", "Speckled Flat Mug", "Flat base ceramic mug with heavy broad handle for cozy tea mornings.", 490.0, 700.0, "30% OFF", 4.8f, null, R.drawable.ceramic_mug, "Clayware", "Stoneware", "PotteryProduct"),
        WishlistItem("6", "Raised-Lip Clay Plate", "Hand-glazed speckled side dining plate with detailed raised edges.", 640.0, 800.0, "20% OFF", 4.6f, null, R.drawable.clay_plate, "Clayware", "Stoneware", "PotteryProduct")
    )

    // 10. Special Offer Products (type = "OfferProduct")
    private val specialOffers = listOf(
        WishlistItem("1", "Ceramic Dining Set", "Earthy Glazed Clay Plate + Speckled Clay Mug. The perfect rustic duo.", 799.0, 1500.0, "BOGO DEAL", 4.8f, null, R.drawable.clay_plate, "Combo Sets", "Save 47%", "OfferProduct"),
        WishlistItem("2", "Boho Living Room Bundle", "Macrame Wall Tapestry + Fringe Linen Cushion Cover.", 3800.0, 6000.0, "BUNDLE DEALS", 4.7f, null, R.drawable.tapestry_wall, "Combo Sets", "Save 36%", "OfferProduct"),
        WishlistItem("3", "Fragrant Serenity Bundle", "Two Soy Wax Clay Candles with hand-painted patterns.", 1300.0, 2000.0, "COMBO DISCOUNT", 4.9f, null, R.drawable.boho_candle, "Curated Sets", "Save 35%", "OfferProduct"),
        WishlistItem("4", "Clayware Starter Kit", "Earthy Ceramic Bowl + Speckled Mug + Rustic Pitcher.", 2600.0, 4200.0, "STARTER KIT", 4.8f, null, R.drawable.ceramic_bowl, "Starter Kits", "Save 38%", "OfferProduct"),
        WishlistItem("5", "Artisan Basket Trio", "Three nesting braided seagrass storage baskets.", 1800.0, 3000.0, "TRIPLE DEAL", 4.6f, null, R.drawable.woven_basket, "Curated Sets", "Save 40%", "OfferProduct")
    )

    // 11. Deal Product (type = "Deal")
    private val deals = listOf(
        WishlistItem("dl1", "Glazed Clay Bowl", "Traditional glazed pottery bowl.", 28.0, 40.0, "30% OFF", 4.6f, null, R.drawable.ceramic_bowl, "Clayware", null, "Deal")
    )

    fun getProduct(id: String, type: String): WishlistItem? {
        val normalizedType = type.lowercase().trim()
        val list = when {
            normalizedType.contains("trending") -> trendingProducts
            normalizedType.contains("ceramic") -> ceramicProducts
            normalizedType.contains("painting") -> paintingProducts
            normalizedType.contains("finearts") -> fineArtsProducts
            normalizedType.contains("artisan") -> artisanProducts
            normalizedType.contains("arrival") -> arrivalProducts
            normalizedType.contains("new") -> newProducts
            normalizedType.contains("pottery") -> potteryProducts
            normalizedType.contains("offer") -> specialOffers
            normalizedType.contains("deal") -> deals
            else -> homeProducts
        }
        
        // Find by exact string ID
        var item = list.find { it.id == id }
        
        // If not found, try to match by converting string ID to int (e.g. "1" -> 1)
        if (item == null) {
            item = list.find { it.id.replace("p", "") == id.replace("p", "") }
        }
        
        // If still not found and we were looking in a specific list, search in all as a fallback
        if (item == null) {
            val allLists = listOf(
                homeProducts, trendingProducts, ceramicProducts, paintingProducts,
                fineArtsProducts, artisanProducts, arrivalProducts, newProducts,
                potteryProducts, specialOffers, deals
            )
            for (fallbackList in allLists) {
                val found = fallbackList.find { it.id == id } ?: fallbackList.find { it.id.replace("p", "") == id.replace("p", "") }
                if (found != null) {
                    return found
                }
            }
        }
        
        return item
    }
}
