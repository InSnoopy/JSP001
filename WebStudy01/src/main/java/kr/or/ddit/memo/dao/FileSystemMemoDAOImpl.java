package kr.or.ddit.memo.dao;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.or.ddit.vo.MemoVO;

public class FileSystemMemoDAOImpl implements MemoDAO {
	// 싱글톤
	private static FileSystemMemoDAOImpl instance;
	public static FileSystemMemoDAOImpl getInstance() {
		if(instance==null)
			instance = new FileSystemMemoDAOImpl();
		return instance;
	}
	
	private File dataBase = new File("d:/memos.dat");
	// DB에서 키로 잡기 위해서 Map으로 만들었다.
	private Map<Integer, MemoVO> memoTable;
	
	// 역직렬화
	private FileSystemMemoDAOImpl() {
		try(
			FileInputStream fis = new FileInputStream(dataBase);
			BufferedInputStream bis = new BufferedInputStream(fis);
			ObjectInputStream ois = new ObjectInputStream(bis);
		){
			memoTable = (Map<Integer, MemoVO>) ois.readObject();
		}catch (Exception e) {
			System.err.println(e.getMessage());
			this.memoTable = new HashMap<>();
		}
	}

	@Override
	public List<MemoVO> selectMemoList() {
		// 반환할 땐 리스트로 반환
		return new ArrayList<>(memoTable.values()); 
	}

	@Override
	public int insertMemo(MemoVO memo) {
		// 옵션 API, 자바8에서 나온 문법 : 간단하다.
		int maxCode = memoTable.keySet().stream()
						.mapToInt(key->key.intValue())
						.max()
						.orElse(0);
		memo.setCode(maxCode+1);
		memoTable.put(memo.getCode(), memo);
		serializeMemoTable();
		return 1;
	}
	
	private void serializeMemoTable() {
		try(
			FileOutputStream fos = new FileOutputStream(dataBase);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
		){
			oos.writeObject(memoTable);
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public int updateMemo(MemoVO memo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteMemo(int code) {
		// TODO Auto-generated method stub
		return 0;
	}
}



