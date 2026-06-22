package com.snehil.minori.core.wishlist

import com.snehil.minori.domain.model.Product
import com.snehil.minori.domain.model.WishlistItem
import com.snehil.minori.mainui.artisanspotlightscreen.model.ArtisanProduct
import com.snehil.minori.mainui.ceramicscreen.model.CeramicProduct
import com.snehil.minori.mainui.dealsofthedayscreen.model.Deal
import com.snehil.minori.mainui.fineartsscreen.model.FineArtsProduct
import com.snehil.minori.mainui.newarrivalsscreen.model.ArrivalProduct
import com.snehil.minori.mainui.newinstorescreen.model.NewProduct
import com.snehil.minori.mainui.paintingscreen.model.PaintingProduct
import com.snehil.minori.mainui.potterypromoscreen.model.PotteryProduct
import com.snehil.minori.mainui.specialoffersscreen.model.OfferProduct
import com.snehil.minori.mainui.trendingproductscreen.model.Product as TrendingProduct

fun Product.toWishlistItem(): WishlistItem = WishlistItem(
    id = id,
    name = name,
    description = description,
    price = price,
    rating = rating,
    imageUrl = imageUrl,
    type = "Product"
)

fun TrendingProduct.toWishlistItem(): WishlistItem = WishlistItem(
    id = id.toString(),
    name = title,
    description = description,
    price = price.toDouble(),
    originalPrice = originalPrice.toDouble(),
    discount = discount,
    rating = rating,
    drawableId = drawableId,
    categoryLabel = category,
    type = "TrendingProduct"
)

fun CeramicProduct.toWishlistItem(): WishlistItem = WishlistItem(
    id = id.toString(),
    name = title,
    description = description,
    price = price.toDouble(),
    originalPrice = originalPrice.toDouble(),
    discount = discount,
    rating = rating,
    drawableId = drawableId,
    categoryLabel = clayType,
    extraTag = firingTemp,
    type = "Ceramic"
)

fun PaintingProduct.toWishlistItem(): WishlistItem = WishlistItem(
    id = id.toString(),
    name = title,
    description = description,
    price = price.toDouble(),
    originalPrice = originalPrice.toDouble(),
    discount = discount,
    rating = rating,
    drawableId = drawableId,
    categoryLabel = medium,
    extraTag = dimensions,
    type = "Painting"
)

fun FineArtsProduct.toWishlistItem(): WishlistItem = WishlistItem(
    id = id.toString(),
    name = title,
    description = description,
    price = price.toDouble(),
    originalPrice = originalPrice.toDouble(),
    discount = discount,
    rating = rating,
    drawableId = drawableId,
    categoryLabel = material,
    extraTag = edition,
    type = "FineArts"
)

fun OfferProduct.toWishlistItem(): WishlistItem = WishlistItem(
    id = id.toString(),
    name = title,
    description = description,
    price = price.toDouble(),
    originalPrice = originalPrice.toDouble(),
    discount = discount,
    rating = rating,
    drawableId = drawableId,
    categoryLabel = category,
    extraTag = offerTag,
    type = "OfferProduct"
)

fun Deal.toWishlistItem(): WishlistItem = WishlistItem(
    id = id.toString(),
    name = title,
    description = description,
    price = price.toDouble(),
    originalPrice = originalPrice.toDouble(),
    discount = discount,
    rating = rating,
    drawableId = drawableId,
    categoryLabel = category,
    type = "Deal"
)

fun NewProduct.toWishlistItem(): WishlistItem = WishlistItem(
    id = id.toString(),
    name = title,
    description = description,
    price = price.toDouble(),
    originalPrice = originalPrice.toDouble(),
    discount = discount,
    rating = rating,
    drawableId = drawableId,
    categoryLabel = category,
    type = "NewProduct"
)

fun ArrivalProduct.toWishlistItem(): WishlistItem = WishlistItem(
    id = id.toString(),
    name = title,
    description = description,
    price = price.toDouble(),
    originalPrice = originalPrice.toDouble(),
    discount = discount,
    rating = rating,
    drawableId = drawableId,
    categoryLabel = category,
    extraTag = materialUsed,
    type = "ArrivalProduct"
)

fun PotteryProduct.toWishlistItem(): WishlistItem = WishlistItem(
    id = id.toString(),
    name = title,
    description = description,
    price = price.toDouble(),
    originalPrice = originalPrice.toDouble(),
    discount = discount,
    rating = rating,
    drawableId = drawableId,
    categoryLabel = category,
    extraTag = difficultyLevel,
    type = "PotteryProduct"
)

fun ArtisanProduct.toWishlistItem(): WishlistItem = WishlistItem(
    id = id.toString(),
    name = title,
    description = description,
    price = price.toDouble(),
    originalPrice = originalPrice.toDouble(),
    discount = discount,
    rating = rating,
    drawableId = drawableId,
    categoryLabel = category,
    extraTag = artisanName,
    type = "ArtisanProduct"
)
