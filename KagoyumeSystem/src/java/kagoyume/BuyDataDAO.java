package kagoyume;

import base.DBManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nakaya-k
 */
public class BuyDataDAO {

    //インスタンスオブジェクトを返却させてコードの簡略化
    public static BuyDataDAO getInstance(){
        return new BuyDataDAO();
    }

    /**
     * データの挿入処理を行う。現在時刻は挿入直前に生成
     * @param bdlist 対応したデータを保持しているJavaBeansのリスト
     * @throws SQLException 呼び出し元にcatchさせるためにスロー
     */
    public void insert(List<BuyDataDTO> bdlist) throws SQLException{
        for(BuyDataDTO bdd : bdlist){
            Connection con = null;
            PreparedStatement st = null;
            try{
                con = DBManager.getConnection();
                st =  con.prepareStatement("INSERT INTO buy_t(userID,itemCode,type,buyDate) VALUES(?,?,?,?)");
                st.setInt(1, bdd.getUserID());
                st.setString(2, bdd.getItemCode());
                st.setInt(3, bdd.getType());
                st.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
                st.executeUpdate();
                System.out.println("insert completed");
            }catch(SQLException e){
                System.out.println(e.getMessage());
                throw new SQLException(e);
            }finally{
                if(con != null){
                    con.close();
                }
                if(st != null){
                    st.close();
                }
            }
        }

    }

    /**
     * データの検索処理を行う。
     * @param ud 対応したデータを保持しているJavaBeans
     * @return 検索結果
     * @throws SQLException 呼び出し元にcatchさせるためにスロー
     */
    public List<BuyDataDTO> search(UserDataDTO ud) throws SQLException{
        Connection con = null;
        PreparedStatement st = null;
        try{
            con = DBManager.getConnection();

            String sql = "SELECT * FROM buy_t WHERE userID = ?";
            st = con.prepareStatement(sql);
            st.setInt(1, ud.getUserID());

            ResultSet rs = null;
            List<BuyDataDTO> bddList = new ArrayList<>();
            try{
                rs = st.executeQuery();
                //  該当データが存在するか確認
                rs.last();
                int length = rs.getRow();
                if(length != 0){
                    rs.beforeFirst();
                    while(rs.next()){
                        BuyDataDTO resultUd = new BuyDataDTO();
                        resultUd.setBuyID(rs.getInt(1));
                        resultUd.setUserID(rs.getInt(2));
                        resultUd.setItemCode(rs.getString(3));
                        resultUd.setType(rs.getInt(4));
                        resultUd.setBuyDate(rs.getTimestamp(5));
                        bddList.add(resultUd);
                    }
                }else{
                    bddList = null;
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
                throw new SQLException(e);
            }finally{
                if(rs != null){
                    rs.close();
                }
            }
            System.out.println("search completed");
            return bddList;
        }catch(SQLException e){
            System.out.println(e.getMessage());
            throw new SQLException(e);
        }finally{
            if(con != null){
                con.close();
            }
            if(st != null){
                st.close();
            }
        }

    }

    /**
     * ユーザーIDによる1件のデータの検索処理を行う。
     * @param ud 対応したデータを保持しているJavaBeans
     * @throws SQLException 呼び出し元にcatchさせるためにスロー
     * @return 検索結果
     */
    /*public UserDataDTO searchByID(UserDataDTO ud) throws SQLException{
        Connection con = null;
        PreparedStatement st = null;
        try{
            con = DBManager.getConnection();

            String sql = "SELECT * FROM user_t WHERE userID = ?";

            st = con.prepareStatement(sql);
            st.setInt(1, ud.getUserID());

            ResultSet rs = null;
            UserDataDTO resultUd = new UserDataDTO();
            try{
                rs = st.executeQuery();
                rs.next();
                resultUd.setUserID(rs.getInt(1));
                resultUd.setName(rs.getString(2));
                resultUd.setBirthday(rs.getDate(3));
                resultUd.setTell(rs.getString(4));
                resultUd.setType(rs.getInt(5));
                resultUd.setComment(rs.getString(6));
                resultUd.setNewDate(rs.getTimestamp(7));
            }catch(SQLException e){
                System.out.println(e.getMessage());
                throw new SQLException(e);
            }finally{
                if(rs != null){
                    rs.close();
                }
            }
            System.out.println("searchByID completed");

            return resultUd;
        }catch(SQLException e){
            System.out.println(e.getMessage());
            throw new SQLException(e);
        }finally{
            if(con != null){
                con.close();
            }
            if(st != null){
                st.close();
            }
        }
    }*/

    /**
     * 名前によるデータの検索処理を行う。
     * @param ud 対応したデータを保持しているJavaBeans
     * @throws SQLException 呼び出し元にcatchさせるためにスロー
     * @return 検索結果
     */
/*    public List<UserDataDTO> searchByName(UserDataDTO ud) throws SQLException{
        Connection con = null;
        PreparedStatement st = null;
        try{
            con = DBManager.getConnection();

            String sql = "SELECT * FROM user_t WHERE name = ?";

            st = con.prepareStatement(sql);
            st.setString(1, ud.getName());

            //検索結果をリストに格納
            ResultSet rs = null;
            List<UserDataDTO> resultList = new ArrayList<UserDataDTO>();
            try{
                rs = st.executeQuery();
                while(rs.next()){
                    UserDataDTO resultUd = new UserDataDTO();
                    resultUd.setUserID(rs.getInt(1));
                    resultUd.setName(rs.getString(2));
                    resultUd.setBirthday(rs.getDate(3));
                    resultUd.setTell(rs.getString(4));
                    resultUd.setType(rs.getInt(5));
                    resultUd.setComment(rs.getString(6));
                    resultUd.setNewDate(rs.getTimestamp(7));
                    resultList.add(resultUd);
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
                throw new SQLException(e);
            }finally{
                if(rs != null){
                    rs.close();
                }
            }
            System.out.println("searchByName completed");

            return resultList;
        }catch(SQLException e){
            System.out.println(e.getMessage());
            throw new SQLException(e);
        }finally{
            if(con != null){
                con.close();
            }
            if(st != null){
                st.close();
            }
        }
    }*/

    /**
     * データの更新処理を行う。現在時刻は挿入直前に生成
     * @param ud 対応したデータを保持しているJavaBeans
     * @throws SQLException 呼び出し元にcatchさせるためにスロー
     */
    public void update(UserDataDTO ud) throws SQLException{
        Connection con = null;
        PreparedStatement st = null;
        try{
            con = DBManager.getConnection();
            st = con.prepareStatement("UPDATE user_t SET name=?,password=?,mail=?,address=?,newDate=?,deleteFlg=? WHERE userID=?");
            st.setString(1, ud.getName());
            st.setString(2, ud.getPassword());
            st.setString(3, ud.getMail());
            st.setString(4, ud.getAddress());
            st.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            st.setInt(6, 0);
            st.setInt(7, ud.getUserID());
            st.executeUpdate();
            System.out.println("update completed");
        }catch(SQLException e){
            System.out.println(e.getMessage());
            throw new SQLException(e);
        }finally{
            if(con != null){
                con.close();
            }
            if(st != null){
                st.close();
            }
        }
    }

    /**
     * ユーザの退会処理を行う。
     * @param ud 対応したデータを保持しているJavaBeans
     * @throws SQLException 呼び出し元にcatchさせるためにスロー
     */
    public void delete(UserDataDTO ud) throws SQLException{
        Connection con = null;
        PreparedStatement st = null;
        try{
            con = DBManager.getConnection();
            st = con.prepareStatement("UPDATE user_t SET deleteFlg=? WHERE userID=?");
            st.setInt(1, 1);
            st.setInt(2, ud.getUserID());
            st.executeUpdate();
            System.out.println("delete completed");
        }catch(SQLException e){
            System.out.println(e.getMessage());
            throw new SQLException(e);
        }finally{
            if(con != null){
                con.close();
            }
            if(st != null){
                st.close();
            }
        }
    }
    
}
