package com.tayler.repository.network.api

import com.tayler.entity.ProductModel
import com.tayler.entity.ProductImageModel
import com.tayler.repository.network.ServiceApi
import com.tayler.repository.network.base.BaseNetwork
import com.tayler.repository.network.model.response.ProductResponse
import com.tayler.repository.network.model.response.ImageMoreResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class DataNetworkTest {

    private lateinit var dataNetwork: DataNetwork
    private val serviceApi: ServiceApi = mockk()
    private val baseNetwork = BaseNetwork()

    @Before
    fun setUp() {
        dataNetwork = DataNetwork(serviceApi, baseNetwork)
    }

    @Test
    fun `loadProduct returns products correctly when not all`() = runTest {
        val mockList = listOf(ProductResponse(uid = "p1", name = "Laptop"))
        coEvery { serviceApi.loadProduct("PE") } returns Response.success(mockList)

        val result = dataNetwork.loadProduct(all = false, isUser = "u1", country = "PE")

        assertEquals(1, result.size)
        assertEquals("Laptop", result[0].name)
    }

    @Test
    fun `loadProduct returns all products correctly`() = runTest {
        val mockList = listOf(ProductResponse(uid = "p1", name = "Global"))
        coEvery { serviceApi.loadProducts("u1") } returns Response.success(mockList)

        val result = dataNetwork.loadProduct(all = true, isUser = "u1", country = "PE")

        assertEquals(1, result.size)
        assertEquals("Global", result[0].name)
    }

    @Test
    fun `saveProduct delegates to serviceApi and returns model`() = runTest {
        val product = ProductModel(uid = "p1", name = "New")
        val response = ProductResponse(uid = "p1", name = "New")
        coEvery { serviceApi.saveProduct(any()) } returns Response.success(response)

        val result = dataNetwork.saveProduct(product)

        assertEquals("p1", result.uid)
    }

    @Test
    fun `updateProduct delegates to serviceApi and returns model`() = runTest {
        val product = ProductModel(uid = "p1", name = "Updated")
        val response = ProductResponse(uid = "p1", name = "Updated")
        coEvery { serviceApi.updateProduct(any()) } returns Response.success(response)

        val result = dataNetwork.updateProduct(product)

        assertEquals("Updated", result.name)
    }

    @Test
    fun `deleteProduct delegates to serviceApi`() = runTest {
        val response = ProductResponse(uid = "p1")
        coEvery { serviceApi.deleteProduct("p1") } returns Response.success(response)

        val result = dataNetwork.deleteProduct("p1")

        assertEquals("p1", result.uid)
    }

    @Test
    fun `loadProductCategory returns products by category`() = runTest {
        val mockList = listOf(ProductResponse(uid = "p1", name = "CatProd"))
        coEvery { serviceApi.loadProductCategory("cat1") } returns Response.success(mockList)

        val result = dataNetwork.loadProductCategory("cat1")

        assertEquals("CatProd", result[0].name)
    }

    @Test
    fun `loadProductImage returns list of images`() = runTest {
        val mockList = listOf(
            ImageMoreResponse(
                uid = "i1", 
                name = "Img", 
                idProduct = "p1", 
                idUser = "u1", 
                url = "url", 
                nameFile = "file"
            )
        )
        coEvery { serviceApi.loadProductImage("p1") } returns Response.success(mockList)

        val result = dataNetwork.loadProductImage("p1")

        assertEquals(1, result.size)
        assertEquals("Img", result[0].name)
    }

    @Test
    fun `saveProductDBImages returns added image`() = runTest {
        val model = ProductImageModel(idProduct = "p1", name = "NewImg")
        val response = ImageMoreResponse(
            uid = "i2", 
            name = "NewImg", 
            idProduct = "p1", 
            idUser = "u1", 
            url = "url", 
            nameFile = "file"
        )
        coEvery { serviceApi.saveProductImages(any()) } returns Response.success(response)

        val result = dataNetwork.saveProductDBImages(model)

        assertEquals("i2", result.uid)
    }
}
