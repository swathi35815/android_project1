/**
 * Data class representing image data.
 * Contains properties for image ID, title, and URL.
 *
 * @property id The unique identifier of the image.
 * @property title The title or description of the image.
 * @property url The URL pointing to the image.
 */
package com.ecommerce.recyclerViewImageData

data class ImageData(var id: Int, var title: String, var url: String) {
}
