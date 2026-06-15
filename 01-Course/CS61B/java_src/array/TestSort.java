package array;
import org.junit.Test;
import static com.google.common.truth.Truth.assertThat;

public class TestSort {
    @Test
    public void testFindSmallest() {
        String[] input = {"i", "have", "an", "egg"};

        int actual = Sort.findSmallest(input, 0);

        assertThat(actual).isEqualTo(2);
    }

    @Test
    public void testFindSmallestWithStart() {
        String[] input = {"i", "have", "an", "egg"};

        int actual = Sort.findSmallest(input, 2);

        assertThat(actual).isEqualTo(2);
    }

    @Test
    public void testSwap() {
        String[] input = {"i", "have", "an", "egg"};
        String[] expected = {"an", "have", "i", "egg"};

        Sort.swap(input, 0, 2);

        assertThat(input).isEqualTo(expected);
    }

    @Test
    public void testSort() {
        String[] input = {"i", "have", "an", "egg"};
        String[] expected = {"an", "egg", "have", "i"};

        Sort.sort(input);

        assertThat(input).isEqualTo(expected);
    }
}
