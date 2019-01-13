package edu.epam.audio.service;

import edu.epam.audio.controller.RequestContent;
import edu.epam.audio.dao.AlbumDao;
import edu.epam.audio.dao.SongDao;
import edu.epam.audio.dao.UserDao;
import edu.epam.audio.dao.impl.AlbumDaoImpl;
import edu.epam.audio.dao.impl.SongDaoImpl;
import edu.epam.audio.dao.impl.UserDaoImpl;
import edu.epam.audio.entity.Album;
import edu.epam.audio.entity.Song;
import edu.epam.audio.entity.User;
import edu.epam.audio.entity.builder.impl.UserBuilder;
import edu.epam.audio.exception.DaoException;
import edu.epam.audio.exception.ServiceException;
import edu.epam.audio.util.ParamsValidator;
import edu.epam.audio.util.UploadPath;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.http.Part;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static edu.epam.audio.util.RequestAttributes.*;
import static edu.epam.audio.util.RequestParams.*;
import static edu.epam.audio.util.SessionAttributes.*;

public class UserService {
    private static final String INCORRECT_BONUS = "label.error.bonus";
    private static final String INCORRECT_LOGIN = "label.error.login";
    private static final String INCORRECT_EMAIL_REG = "label.error.email";
    private static final String NO_SUCH_USER = "label.error.no.user";
    private static final String INCORRECT_BONUS_PAYMENT = "label.error.no.bonus";
    private static final String INCORRECT_POCKET_PAYMENT = "label.error.no.money";
    private static final String INCORRECT_NAME_REG = "label.error.name";
    private static final String INCORRECT_PASSWORD_REGEX_MES = "label.error.regex.password";
    private static final String INCORRECT_NAME_MES = "label.error.regex.name";
    private static final String INCORRECT_MONEY = "label.error.money";
    private static final String INCORRECT_MAX_MONEY = "label.error.money.limit";
    private static final String YOU_ALREADY_HAVE_SONG = "label.repeat.buy.song";
    private static final String YOU_ALREADY_HAVE_ALBUM = "label.repeat.buy.album";

    public void loginUser(RequestContent content) throws ServiceException {
        UserDao userDao = UserDaoImpl.getInstance();
        String email = content.getRequestParam(PARAM_NAME_EMAIL);
        String password = content.getRequestParam(PARAM_NAME_PASSWORD);
        String encryptedPassword = DigestUtils.md5Hex(password);
        User potentialUser = new UserBuilder()
                .addEmail(email)
                .build();
        try {
            Optional<User> userFromDb = userDao.findUserByEmail(potentialUser);
            if (userFromDb.isPresent() && userFromDb.get().getPassword().equals(encryptedPassword)) {
                content.setSessionAttribute(SESSION_ATTRIBUTE_USER, userFromDb.get());
            } else {
                content.setRequestAttribute(ATTRIBUTE_NAME_ERROR, INCORRECT_LOGIN);
            }
        } catch (DaoException e) {
            throw new ServiceException("Exception while logging into app.", e);
        }
    }

    public void registerUser(RequestContent content) throws ServiceException {
        UserDao userDao = UserDaoImpl.getInstance();
        String email = content.getRequestParam(PARAM_NAME_EMAIL);
        String password = content.getRequestParam(PARAM_NAME_PASSWORD);
        String name = content.getRequestParam(PARAM_NAME_NICK);
        User user = new UserBuilder()
                .addEmail(email)
                .addPassword(password)
                .addName(name)
                .build();
        try {
            Optional<User> userFormDb = userDao.findUserByEmail(user);
            if (!userFormDb.isPresent()) {
                if (ParamsValidator.validatePassword(password)) {
                    if (ParamsValidator.validateName(name)) {
                        userFormDb = userDao.findUserByName(user);
                        if (!userFormDb.isPresent()) {
                            String encryptedPassword = DigestUtils.md5Hex(password);
                            user.setPassword(encryptedPassword);
                            long id = userDao.create(user);
                            Optional<User> optionalUser = userDao.findEntityById(id);
                            if (!optionalUser.isPresent()) {
                                throw new ServiceException("Can't add user.");
                            }
                            user = optionalUser.get();
                            content.setSessionAttribute(SESSION_ATTRIBUTE_USER, user);
                        } else {
                            content.setRequestAttribute(ATTRIBUTE_NAME_ERROR, INCORRECT_NAME_REG);
                        }
                    } else {
                        content.setRequestAttribute(ATTRIBUTE_NAME_ERROR, INCORRECT_NAME_MES);
                    }
                } else {
                    content.setRequestAttribute(ATTRIBUTE_NAME_ERROR, INCORRECT_PASSWORD_REGEX_MES);
                }
            } else {
                content.setRequestAttribute(ATTRIBUTE_NAME_ERROR, INCORRECT_EMAIL_REG);
            }
        } catch (DaoException e) {
            throw new ServiceException("Exception while register.", e);
        }
    }

    public void updateProfile(RequestContent content) throws ServiceException {
        UserDao userDao = UserDaoImpl.getInstance();
        User user = (User) content.getSessionAttribute(SESSION_ATTRIBUTE_USER);
        String email = content.getRequestParam(PARAM_NAME_EMAIL);
        String name = content.getRequestParam(PARAM_NAME_NICK);
        String path = (String) content.getRequestAttribute(PARAM_NAME_PATH);

        try {
            User updatedUser = user.clone();
            updatedUser.setEmail(email);
            if (ParamsValidator.validateName(name)) {
                updatedUser.setName(name);
                Optional<User> userByEmail = userDao.findUserByEmail(updatedUser);
                if (!userByEmail.isPresent() || userByEmail.get().getUserId() == updatedUser.getUserId()) {
                    Optional<User> userByName = userDao.findUserByName(updatedUser);
                    if (!userByName.isPresent() || userByName.get().getUserId() == user.getUserId()) {
                        File fileSaveDir = new File(path);
                        if (!fileSaveDir.exists()) {
                            fileSaveDir.mkdirs();
                        }

                        Part part = content.getRequestPart(PARAM_NAME_PHOTO);
                        String loadPath = UploadPath.uploadPhoto(path, part);
                        if (loadPath != null) {
                            updatedUser.setPhoto(loadPath);
                        }
                        userDao.update(updatedUser);
                        content.setSessionAttribute(SESSION_ATTRIBUTE_USER, updatedUser);
                    } else {
                        content.setRequestAttribute(ATTRIBUTE_NAME_ERROR, INCORRECT_NAME_MES);
                    }
                } else {
                    content.setRequestAttribute(ATTRIBUTE_NAME_ERROR, INCORRECT_EMAIL_REG);
                }
            } else {
                content.setRequestAttribute(ATTRIBUTE_NAME_ERROR, INCORRECT_NAME_REG);
            }
        } catch (CloneNotSupportedException e) {
            throw new ServiceException("Exception in cloning user.", e);
        } catch (DaoException e) {
            throw new ServiceException("Exception in getting user from db.", e);
        }
    }

    public void updateBonus(RequestContent content) throws ServiceException {
        long id = Long.parseLong(content.getRequestParam(PARAM_NAME_ID));
        String strBonus = content.getRequestParam(PARAM_NAME_BONUS);
        UserDao userDao = UserDaoImpl.getInstance();
        try {
            double bonus = Double.parseDouble(strBonus);
            if (ParamsValidator.validateMoney(bonus)) {
                Optional<User> user = userDao.findEntityById(id);
                if (user.isPresent()) {
                    User userObj = user.get();
                    bonus = Math.round(bonus*100)/100;
                    userObj.setBonus(bonus);
                    userDao.update(userObj);
                } else {
                    content.setRequestAttribute(ATTRIBUTE_NAME_ERROR, NO_SUCH_USER);
                }
            } else {
                content.setRequestAttribute(ATTRIBUTE_NAME_ERROR, INCORRECT_MAX_MONEY);
            }
        } catch (NumberFormatException e) {
            content.setRequestAttribute(ATTRIBUTE_NAME_ERROR, INCORRECT_BONUS);
        } catch (DaoException e) {
            throw new ServiceException("Exception in getting user from db.", e);
        }
    }

    public List<User> loadAllUsers() throws ServiceException {
        UserDao userDao = UserDaoImpl.getInstance();
        List<User> users;
        try {
            users = userDao.findAll();
            return users;
        } catch (DaoException e) {
            throw new ServiceException("Exception in getting all the users from db.", e);
        }
    }

    public void addMoney(RequestContent content) throws ServiceException {
        User user = (User) content.getSessionAttribute(SESSION_ATTRIBUTE_USER);
        UserDao userDao = UserDaoImpl.getInstance();
        try {
            double money = Double.parseDouble(content.getRequestParam(PARAM_NAME_MONEY));
            money += user.getMoney();
            if (ParamsValidator.validateMoney(money)) {
                User clone = user.clone();
                money = Math.round(money*100)/100;
                clone.setMoney(money);
                userDao.update(clone);
                content.setSessionAttribute(SESSION_ATTRIBUTE_USER, clone);
            } else {
                content.setRequestAttribute(ATTRIBUTE_NAME_ERROR, INCORRECT_MAX_MONEY);
            }
        } catch (NumberFormatException e) {
            content.setRequestAttribute(ATTRIBUTE_NAME_ERROR, INCORRECT_MONEY);
        } catch (DaoException e) {
            throw new ServiceException("Exception while adding money.", e);
        } catch (CloneNotSupportedException e) {
            throw new ServiceException("Exception in cloning.", e);
        }
    }

    public void buySong(RequestContent content) throws ServiceException {
        SongDao songDao = SongDaoImpl.getInstance();
        UserDao userDao = UserDaoImpl.getInstance();
        User user = (User) content.getSessionAttribute(SESSION_ATTRIBUTE_USER);
        Long songId = Long.parseLong(content.getRequestParam(PARAM_NAME_ID));
        String paymentType = content.getRequestParam(PARAM_NAME_PAYMENT);
        double bonus;
        double cost;
        double money;
        try {
            Optional<Song> songOptional = songDao.findEntityById(songId);
            if (!songOptional.isPresent()) {
                throw new ServiceException("No such song in db.");
            }
            Song song = songOptional.get();
            List<Song> userSongs = songDao.findUserSongs(user);
            if (!userSongs.contains(song)) {
                User clone = user.clone();
                switch (paymentType) {
                    case PARAM_VALUE_BONUS:
                        bonus = user.getBonus();
                        cost = song.getCost();
                        if (bonus >= cost) {
                            clone.setBonus(Math.round((bonus - cost)*100)/100);
                            userDao.buySong(clone, song);
                            content.setSessionAttribute(SESSION_ATTRIBUTE_USER, clone);
                        } else {
                            content.setRequestAttribute(ATTRIBUTE_NAME_ERROR, INCORRECT_BONUS_PAYMENT);
                        }
                        break;
                    case PARAM_VALUE_POCKET:
                        money = user.getMoney();
                        cost = song.getCost();
                        if (money >= cost) {
                            clone.setMoney(Math.round((money - cost)*100)/100);
                            userDao.buySong(clone, song);
                            content.setSessionAttribute(SESSION_ATTRIBUTE_USER, clone);
                        } else {
                            content.setRequestAttribute(ATTRIBUTE_NAME_ERROR, INCORRECT_POCKET_PAYMENT);
                        }
                        break;
                    default:
                        throw new ServiceException("Unknown parameter : " + paymentType);
                }
            } else {
                content.setRequestAttribute(ATTRIBUTE_NAME_ERROR, YOU_ALREADY_HAVE_SONG);
            }
        } catch (DaoException e) {
            throw new ServiceException("Exception in payment.", e);
        } catch (CloneNotSupportedException e) {
            throw new ServiceException("Exception in cloning.", e);
        }
    }

    public void buyAlbum(RequestContent content) throws ServiceException {
        AlbumDao albumDao = AlbumDaoImpl.getInstance();
        SongDao songDao = SongDaoImpl.getInstance();
        UserDao userDao = UserDaoImpl.getInstance();
        User user = (User) content.getSessionAttribute(SESSION_ATTRIBUTE_USER);
        Long albumId = Long.parseLong(content.getRequestParam(PARAM_NAME_ID));
        String paymentType = content.getRequestParam(PARAM_NAME_PAYMENT);
        double bonus;
        double cost;
        double money;
        try {
            Optional<Album> albumOptional = albumDao.findEntityById(albumId);
            if (!albumOptional.isPresent()) {
                throw new ServiceException("No such album in db.");
            }
            Album album = albumOptional.get();
            List<Song> songs = songDao.findSongsByAlbum(album);
            List<Song> userSongs = songDao.findUserSongs(user);
            songs.removeAll(userSongs);
            album.setSongs(songs);
            List<Album> userAlbums = albumDao.findUserAlbums(user);
            List<Double> costs = new ArrayList<>();
            album.getSongs().forEach(song -> costs.add(song.getCost()));
            Optional<Double> reduce = costs.stream().reduce((x, y) -> x+y);
            cost = reduce.isPresent() ? reduce.get() : 0;
            if (!userAlbums.contains(album)) {
                User clone = user.clone();
                switch (paymentType) {
                    case PARAM_VALUE_BONUS:
                        bonus = user.getBonus();
                        if (bonus >= cost) {
                            clone.setBonus(Math.round((bonus - cost)*100)/100);
                            userDao.buyAlbum(clone, album);
                            content.setSessionAttribute(SESSION_ATTRIBUTE_USER, clone);
                        } else {
                            content.setRequestAttribute(ATTRIBUTE_NAME_ERROR, INCORRECT_BONUS_PAYMENT);
                        }
                        break;
                    case PARAM_VALUE_POCKET:
                        money = user.getMoney();
                        if (money >= cost) {
                            clone.setMoney(Math.round((money - cost)*100)/100);
                            userDao.buyAlbum(clone, album);
                            content.setSessionAttribute(SESSION_ATTRIBUTE_USER, clone);
                        } else {
                            content.setRequestAttribute(ATTRIBUTE_NAME_ERROR, INCORRECT_POCKET_PAYMENT);
                        }
                        break;
                    default:
                        throw new ServiceException("Unknown parameter : " + paymentType);
                }
            } else {
                content.setRequestAttribute(ATTRIBUTE_NAME_ERROR, YOU_ALREADY_HAVE_ALBUM);
            }
        } catch (DaoException e) {
            throw new ServiceException("Exception in payment.", e);
        } catch (CloneNotSupportedException e) {
            throw new ServiceException("Exception in cloning.", e);
        }
    }
}
