package com.example.board.utils;

import lombok.Getter;
import org.springframework.data.domain.Page;
public class PagingUtils {

    public static PagingInfo calculatePagingInfo(Page<?> page) {
        int currentPage = page.getNumber(); // 현재 페이지 번호
        int totalPages = page.getTotalPages(); // 총 페이지 수
        int countPage = (currentPage) / 5;
        int countEnd = (totalPages-1)/5;

        int startPage = countPage * 5 + 1;
        int endPage = startPage+4;

        int nextPageGroupStart = countPage*5+5;
        int prevPageGroupEnd = countPage*5-1;

        return new PagingInfo(startPage, endPage, nextPageGroupStart, prevPageGroupEnd, currentPage, totalPages, countEnd);
    }

    public static class PagingInfo {
        private final int startPage;
        private final int endPage;
        private final int nextPageGroupStart;
        private final int prevPageGroupEnd;
        private final int currentPage;
        private final int totalPages;
        private  final int countEnd;

        public PagingInfo(int startPage, int endPage, int nextPageGroupStart, int prevPageGroupEnd, int currentPage, int totalPages, int countEnd) {
            this.startPage = startPage;
            this.endPage = endPage;
            this.nextPageGroupStart = nextPageGroupStart;
            this.prevPageGroupEnd = prevPageGroupEnd;
            this.currentPage = currentPage;
            this.totalPages = totalPages;
            this.countEnd = countEnd;
        }

        // Getter 메서드들
        public int getStartPage() {
            return startPage;
        }

        public int getEndPage() {
            return endPage;
        }

        public int getNextPageGroupStart() {
            return nextPageGroupStart;
        }

        public int getPrevPageGroupEnd() {
            return prevPageGroupEnd;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public int getCountEnd() {
            return countEnd;
        }
    }
}
