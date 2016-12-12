package pl.hypeapp.wykopolka.network.api;

import java.util.List;

import okhttp3.ResponseBody;
import pl.hypeapp.wykopolka.model.Book;
import pl.hypeapp.wykopolka.model.SearchResult;
import pl.hypeapp.wykopolka.model.Statistics;
import pl.hypeapp.wykopolka.model.User;
import pl.hypeapp.wykopolka.model.WishBookStatus;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import rx.Observable;

public interface WykopolkaApi {

    @FormUrlEncoded
    @POST("wykopolka/public/api/request/user/get")
    Observable<User> getUser(@Field("accountkey") String accountKey, @Header("apisign") String apisign);

    @FormUrlEncoded
    @POST("wykopolka/public/api/request/book")
    Observable<List<Book>> getBook(@Field("id") String bookId, @Header("apisign") String apisign);

    @FormUrlEncoded
    @POST("wykopolka/public/api/request/wishlist/book/status")
    Observable<WishBookStatus> getWishBookStatus(@Field("accountkey") String accountkey, @Field("id") String bookId, @Header("apisign") String apisign);

    @FormUrlEncoded
    @POST("wykopolka/public/api/request/user/books/added")
    Observable<List<Book>> getUserAddedBooks(@Field("accountkey") String accountKey, @Header("apisign") String apisign);

    @FormUrlEncoded
    @POST("wykopolka/public/api/request/user/books/owned")
    Observable<List<Book>> getMyBooks(@Field("accountkey") String accountKey, @Header("apisign") String apisign);

    @FormUrlEncoded
    @POST("wykopolka/public/api/request/book/selected")
    Observable<List<Book>> getSelectedBooks(@Field("accountkey") String accountKey, @Field("amount") String amount, @Header("apisign") String apisign);

    @FormUrlEncoded
    @POST("wykopolka/public/api/request/book/random")
    Observable<Book> getRandomBook(@Field("accountkey") String accountKey, @Header("apisign") String apisign);

    @FormUrlEncoded
    @POST("wykopolka/public/api/request/stats/global")
    Observable<Statistics> getGlobalStats(@Field("accountkey") String accountKey, @Header("apisign") String apisign);

    @FormUrlEncoded
    @POST("wykopolka/public/api/request/book/search")
    Observable<SearchResult> searchByQuery(@Field("booksearch") String query, @Header("apisign") String apisign);

    //    @FormUrlEncoded
//    @POST("wykopolka/public/api/request/book/add")
//    Observable<SearchResult> uploadBook(@Field("accountkey") String acccountKey, @Field("book") String book, @Header("apisign") String apisign);
    @FormUrlEncoded
    @POST("wykopolka/public/api/request/book/add")
    Call<ResponseBody> uploadBook(@Field("accountkey") String accountKey, @Field("book") String book, @Header("apisign") String apisign);

    /**
     * @param accountKey
     * @param bookId
     * @param state      (0 - remove from wish list 1 - add to wish list)
     * @param apisign
     * @return
     */
    @FormUrlEncoded
    @POST("wykopolka/public/api/request/wishlist/manage")
    Call<ResponseBody> manageWishList(@Field("accountkey") String accountKey, @Field("id") String bookId, @Field("state") String state, @Header("apisign") String apisign);

    @FormUrlEncoded
    @POST("wykopolka/public/api/request/book/find/by/author")
    Call<ResponseBody> searchByAuthor(@Field("booksearch") String author, @Header("apisign") String apisign);

    @Deprecated
    @FormUrlEncoded
    @POST("wykopolka/public/api/request/user/getloginbyid")
    Call<ResponseBody> getLoginById(@Field("id") String userId, @Header("apisign") String apisign);

    @Deprecated
    @FormUrlEncoded
    @POST("wykopolka/public/api/request/book/iswishlisted")
    Call<ResponseBody> isWishlisted(@Field("accountkey") String accountKey, @Field("id") String bookId, @Header("apisign") String apisign);

    @Deprecated
    @FormUrlEncoded
    @POST("wykopolka/public/api/request/book/iswishlistingallowed")
    Call<ResponseBody> isWishlistingAllowed(@Field("accountkey") String accountKey, @Field("id") String bookId, @Header("apisign") String apisign);

}
