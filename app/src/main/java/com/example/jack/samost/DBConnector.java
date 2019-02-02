package com.example.jack.samost;

import android.os.AsyncTask;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBConnector {

    private static ConnectionSource connectionSource;
    private static Dao<Product, Integer> productDao;
    private static Dao<User, Integer> userDao;
    public static AsyncResponse delegate = null;
    private static Connection conn;
    private static String url = "jdbc:mysql://195.251.166.34/samosbay";

    public static void connectAndInsert() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        Class.forName("com.mysql.jdbc.Driver").newInstance();

        // create a connection source to our database
        connectionSource =
                new JdbcConnectionSource(url, "root", "sanity");
        productDao =
                DaoManager.createDao(connectionSource, Product.class);
        userDao =
                DaoManager.createDao(connectionSource, User.class);
        //dimiourgei ton pinaka ean den iparxei kata tin sundesi
        new createTable().execute();
    }


    public static void insert(Product product) {
        new InsertToTable(product).execute();
    }

    public static void getProductList() {
        FetchProducts fetch = new FetchProducts();
        fetch.execute();
    }

    private static class createTable extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                TableUtils.createTableIfNotExists(connectionSource, Product.class);
                TableUtils.createTableIfNotExists(connectionSource, User.class);
                getProductList();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    private static class InsertToTable extends AsyncTask<Void, Void, Void> {
        Product product;

        public InsertToTable(Product product) {
            super();
            this.product = product;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                productDao.create(product);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return null;
        }
    }


    private static class FetchProducts extends AsyncTask<ConnectionSource, Void, ArrayList<Product>> {

        @Override
        protected ArrayList<Product> doInBackground(ConnectionSource... connectionSources) {
            //get list with products from DB
//            try {
//                QueryBuilder<Product, Integer> queryBuilder =
//                        productDao.queryBuilder();
//                PreparedQuery<Product> preparedQuery = queryBuilder.prepare();
//                Log.d("Select statement", "reached" );
//                List<Product> itemList = productDao.query(preparedQuery);
//                Log.d("Found", itemList.get(0).getName());
//
//                return new ArrayList<>(itemList);
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
            try {
                conn = DriverManager.getConnection(url, "root", "sanity");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ArrayList<Product> products  = new ArrayList<>();
            Statement stmt = null;
            try {
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM product");
                while (rs.next()) {
                    Product prod = new Product();
                    prod.setName(rs.getString("name"));
                    prod.setDescription(rs.getString("description"));
                    prod.setTags(rs.getString("tags"));
                    prod.setSeller(rs.getString("seller"));
                    prod.setCategory(rs.getString("category"));
                    prod.setPrice(rs.getFloat("price"));
                    prod.setCreationDate(rs.getDate("creation_date"));
                    //Date cdate = rs.getString("creation_date");
                    //String seller = rs.getString("seller");
                    //Log.d("RESULT ", lastName);
                    InputStream input = rs.getBinaryStream("image");
                    byte[] buf = new byte[2048];
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    try {
                        for (int readNum; (readNum = input.read(buf)) != -1; ) {
                            bos.write(buf, 0, readNum); //no doubt here is 0
                            //Writes len bytes from the specified byte array starting at offset off to this byte array output stream.
                            System.out.println("read " + readNum + " bytes,");
                        }
                    } catch (IOException ex) {
                        Log.e("Error", ex.getMessage());
                    }
                    prod.setImage(bos.toByteArray());
                    products.add(prod);
                }

                rs.close();
                conn.close();
                return products;
            } catch (SQLException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Product> products) {
            delegate.processFinish(products);
        }

    }

}
